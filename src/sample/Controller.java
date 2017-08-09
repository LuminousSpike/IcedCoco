package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class Controller implements Initializable{

    private ResourceBundle resources;
    private SessionInfo sessionInfo = new SessionInfo();
    private Scene scene;
    private Stage primaryStage;
    private float canvasZoomAmount = 0.05f;   // as a percentage, from 0 - 1
    private float minCanvasSize = 100f;     // min size for both of the width and height of the canvas
    @FXML private GridPane masterPane;
    @FXML private ScrollPane canvasScrollPane;
    @FXML private AnchorPane canvasAnchorPane; // canvas is child of the anchor pane, anchor pane is child of the scroll pane
    @FXML private MenuItem saveMenuItem;
    @FXML private Canvas canvas;

    @FXML
    private Button tool1;
    @FXML
    private Button tool2;

    private LinkedList<Polygon> polygons;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        canvasScrollPane.setContent(canvas);

        polygons = new LinkedList<>();
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
    }

    public void setScene(Scene scene){
        this.scene = scene;
    }

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    private double[] getCanvasArea(){
        // return the width and height as [width,height], of the space the canvas can expand to fill
        // canvas can occupy area from (1,1) to (3,5) of the gridpane. (column, row)
        double w = canvasScrollPane.getViewportBounds().getWidth();
        double h = canvasScrollPane.getViewportBounds().getHeight();
        return new double[] {w, h};
    }

    private void drawImageInCanvas(Image img){
        if(img==null){
            // for debugging
            System.err.println("Error: image passed to drawImageInCanvas is null");
            return;
        }
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        // draw the image size to be its actual size if it can fit within the window,
        // or shrunk to fit the size of the canvas at its biggest
        // change this when we implement zooming maybe
        double[] paneBounds = getCanvasArea();
        if(img.getWidth() < paneBounds[0] && img.getHeight() < paneBounds[1]) {
            canvas.setWidth(img.getWidth());
            canvas.setHeight(img.getHeight());
        }
        else{
            double shrinkFactor = 0;
            if (img.getWidth()/paneBounds[0] > img.getHeight()/paneBounds[1]){
                shrinkFactor = paneBounds[0] / img.getWidth();
            }
            else{
                shrinkFactor = paneBounds[1] / img.getHeight();
            }
            canvas.setWidth(img.getWidth() * shrinkFactor);
            canvas.setHeight(img.getHeight() * shrinkFactor);
        }
        gfx.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight());

        //sessionInfo settings
        sessionInfo.baseImage = img;
        sessionInfo.imageWidth = img.getWidth();
        sessionInfo.imageHeight = img.getHeight();
    }

    // called from listeners defined in Main.java
    public void onWindowResize(){
        return;
    }

    @FXML
    public void newMetadataSet(ActionEvent event){

    }

    @FXML
    public void growCanvas(Event event){
        // change by ten percent
        if(sessionInfo.baseImage==null){
            return;
        }
        // important to floor, otherwise the actual canvas size may end up one pixel larger than the image drawn inside it
        canvas.setWidth(Math.floor(canvas.getWidth() + canvasZoomAmount * sessionInfo.baseImage.getWidth()));
        canvas.setHeight(Math.floor(canvas.getHeight() + canvasZoomAmount * sessionInfo.baseImage.getHeight()));
        canvas.getGraphicsContext2D().drawImage(sessionInfo.baseImage, 0,0,canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    public void shrinkCanvas(Event event){
        if(sessionInfo.baseImage==null){
            return;
        }
        double newWidth = Math.floor(canvas.getWidth() - canvasZoomAmount * sessionInfo.baseImage.getWidth());
        double newHeight = Math.floor(canvas.getHeight() - canvasZoomAmount * sessionInfo.baseImage.getHeight());
        if(newWidth < minCanvasSize || newHeight < minCanvasSize){
            return;
        }
        canvas.setWidth(newWidth);
        canvas.setHeight(newHeight);
        canvas.getGraphicsContext2D().drawImage(sessionInfo.baseImage, 0,0,canvas.getWidth(), canvas.getHeight());
    }

    @FXML
    public void prepareFileMenu(Event event){
        // validate all the menu items in the menu file
        // disable save if there is no image loaded and no valid .json files to save the metadata to
        saveMenuItem.setDisable(!(sessionInfo.imageLoaded && sessionInfo.saveFilesReady()));
    }


    @FXML
    public void menuOpenImage(ActionEvent event){
        // load the image to the canvas without any metadata
        System.out.println("Open file");
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Images", "*.jpeg", "*.jpg", "*.JPG","*.JPEG", "*.png", "*.PNG", "*.gif", "*.GIF");
        // a catch all filter incase there is some weird image format we miss
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        fc.getExtensionFilters().add(imgFilter);
        File imgFile = fc.showOpenDialog(scene.getWindow());
        try {
            if (imgFile != null) {
                Image img = new Image(imgFile.toURI().toURL().toExternalForm());       // test this works on all systems
                drawImageInCanvas(img);
            }
        }catch(MalformedURLException mue){
            mue.printStackTrace();
        }
    }


    @FXML
    public void menuOpenImageWithCoco(ActionEvent event){
        // load the image to the canvas with the metadata (make default opening method?)
        System.out.println("Open image with metadata");
    }

    @FXML
    public void menuSaveData(ActionEvent event){
        System.out.println("saving to .json files...");
    }

    @FXML
    public void menuExit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    private void onMouseClickedListener_Canvas (MouseEvent e) {
        Vertex selectedVertex = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                if (polygons.get(polygons.size() - 1).size() > 2) {
                    polygons.add(new Polygon());
                }
                break;
            }
        }

        // TODO: Check if we really need this if statement.
        if (selectedVertex == null) {
            Polygon p = null;

            p = polygons.getLast();
            p.add(e.getX(), e.getY());

            draw(canvas);
        }
    }

    @FXML
    private void onMousePressedListener_Canvas (MouseEvent e) {
        Vertex selectedVertex = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected())!= null) {
                selectedVertex.updateSelected(false);
            }

            p.setVertexColor(Color.BLUE);

            if ((selectedVertex = p.find(e.getX(), e.getY())) != null) {
                selectedVertex.updateColor(Color.RED);
                selectedVertex.updateSelected(true);
                draw(canvas);
            }
        }
    }

    @FXML
    private void onMouseReleasedListener_Canvas (MouseEvent e) {

    }

    @FXML
    private void onDragEnteredListener_Canvas (MouseEvent e) {
        Vertex v = null;

        for (Polygon p : polygons) {
            p.setVertexColor (Color.BLUE);

            if ((v = p.find(e.getX(), e.getY())) != null) {
                v.updateColor(Color.RED);
            }
        }

        if (v != null) {
            v.updateAxisX(e.getX());
            v.updateAxisY(e.getY());
        }
    }

    @FXML
    private void onMouseDraggedListener_Canvas (MouseEvent e) {
        Vertex selectedVertex  = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                selectedVertex.updateAxisX(e.getX());
                selectedVertex.updateAxisY(e.getY());
                draw(canvas);
            }
        }
    }

    private void draw(Canvas canvas)
    {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for (Polygon p : polygons) {
            p.draws(gc);
        }
    }
}
