package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private ResourceBundle resources;
    private SessionInfo sessionInfo = new SessionInfo();
    private Scene scene;
    private Stage primaryStage;
    private float canvasZoomAmount = 0.05f;   // as a percentage, from 0 - 1
    private float minCanvasSize = 100f;     // min size for both of the width and height of the canvas
    private Image img;

    @FXML private GridPane masterPane;
    @FXML private ScrollPane canvasScrollPane;
    @FXML private AnchorPane canvasAnchorPane; // canvas is child of the anchor pane, anchor pane is child of the scroll pane
    @FXML private MenuItem saveMenuItem;
    @FXML private Canvas canvas;
    @FXML private Button tool1;
    @FXML private Button tool2;

    private PolygonTool polygonTool;
    private EllipseTool ellipseTool;
    private Tool currentTool = null;

    private LinkedList<Polygon> polygons = new LinkedList<Polygon>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        canvasScrollPane.setContent(canvas);
        polygonTool = new PolygonTool(polygons);
        polygonTool.setCanvas(canvas);

        ellipseTool = new EllipseTool(polygons);
        ellipseTool.setCanvas(canvas);
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

    private void drawImageInCanvas(Image img, boolean newImage){
        if(img==null){
            // do nothing
            return;
        }
        GraphicsContext gfx = canvas.getGraphicsContext2D();
        gfx.clearRect(0, 0, gfx.getCanvas().getWidth(), gfx.getCanvas().getHeight());
        // draw the image size to be its actual size if it can fit within the window,
        // or shrunk to fit the size of the canvas at its biggest
        // change this when we implement zooming maybe
        if (newImage) {
            double[] paneBounds = getCanvasArea();
            if (img.getWidth() < paneBounds[0] && img.getHeight() < paneBounds[1]) {
                canvas.setWidth(img.getWidth());
                canvas.setHeight(img.getHeight());
            } else {
                double shrinkFactor = 0;
                if (img.getWidth() / paneBounds[0] > img.getHeight() / paneBounds[1]) {
                    shrinkFactor = paneBounds[0] / img.getWidth();
                } else {
                    shrinkFactor = paneBounds[1] / img.getHeight();
                }
                canvas.setWidth(img.getWidth() * shrinkFactor);
                canvas.setHeight(img.getHeight() * shrinkFactor);
            }
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
    public void OpenHelpWindow(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form_help.fxml"));
            Parent root = loader.load();
            ControllerHelp cont = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    public void createMetadataFiles(ActionEvent event){
        Parent createFilesRoot;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form_createMetadata.fxml"));
            createFilesRoot = loader.load();
            CreateMetadataController cont = loader.getController();
            cont.setSessionInfo(this.sessionInfo);
            Scene scene = new Scene(createFilesRoot, 640, 480);
            cont.setScene(scene);
            Stage popup = new Stage();
            popup.setScene(scene);
            popup.show();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    @FXML
    public void selectMetadataFiles(ActionEvent evet){
        Parent createFilesRoot;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("form_selectMetadata.fxml"));
            createFilesRoot = loader.load();
            SelectMetadataController cont = loader.getController();
            cont.setSessionInfo(this.sessionInfo);
            Scene scene = new Scene(createFilesRoot, 640, 480);
            cont.setScene(scene);
            Stage popup = new Stage();
            popup.setScene(scene);
            popup.show();
            cont.start();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
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
        if(currentTool!=null) currentTool.draw();
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
        if(currentTool!=null) currentTool.draw();
    }

    @FXML
    public void prepareFileMenu(Event event){
        // validate all the menu items in the menu file
        // disable save if there is no image loaded and no valid .json files to save the metadata to
        saveMenuItem.setDisable(sessionInfo.baseImage==null | sessionInfo.imageDataFile==null);
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
                img = new Image(imgFile.toURI().toURL().toExternalForm());       // test this works on all systems
                // TODO: Implement a proper way to initialize tools upon image load.
                // For now, set the current tool to null.
                currentTool = null;
                // And make a new PolygonTool.
                polygons = new LinkedList<Polygon>();
                polygonTool = new PolygonTool(polygons);
                polygonTool.setCanvas(canvas);
                ellipseTool = new EllipseTool(polygons);
                ellipseTool.setCanvas(canvas);
                drawImageInCanvas(img, true);
                sessionInfo.checkImageMetadata();
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
        sessionInfo.overwriteMetadata(primaryStage);
    }

    @FXML
    public void menuExit(ActionEvent event) {
        Platform.exit();
    }


    @FXML
    private void onMouseClickedListener_Canvas (MouseEvent e) {
        if (currentTool != null) {
            drawImageInCanvas(img, false);
            currentTool.onMouseClicked(e);
        }
    }

    @FXML
    private void onMousePressedListener_Canvas (MouseEvent e) {
        if (currentTool != null) {
            drawImageInCanvas(img, false);
            currentTool.onMousePressed(e);
        }
    }

    @FXML
    private void onMouseReleasedListener_Canvas (MouseEvent e) {
        if (currentTool != null) {
            drawImageInCanvas(img, false);
            currentTool.onMouseReleased(e);
        }
    }

    @FXML
    private void onDragEnteredListener_Canvas (MouseEvent e) {
        if (currentTool != null) {
            drawImageInCanvas(img, false);
            currentTool.onDragEntered(e);
        }
    }

    @FXML

    private void onMouseDraggedListener_Canvas (MouseEvent e) {
        if (currentTool != null) {
            drawImageInCanvas(img, false);
            currentTool.onMouseDragged(e);
        }
    }

    @FXML
    private void activatePolygonTool () {
        currentTool = polygonTool;
    }

    @FXML
    private void activateEllipseTool () {
        currentTool = ellipseTool;
    }
}
