package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.annotation.Resources;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private ResourceBundle resources;
    private SessionInfo sessionInfo = new SessionInfo();
    private Scene scene;
    @FXML private GridPane masterPane;
    @FXML private AnchorPane canvasAnchorPane;
    @FXML private MenuBar menuBar;
    @FXML private MenuItem saveMenuItem;
    @FXML private Menu fileMenu;
    @FXML private Canvas canvas;


    public void setScene(Scene scene){
        this.scene = scene;
    }

    private double[] getCanvasArea(){
        // return the width and height as [width,height], of the space the canvas can expand to fill
        // canvas can occupy area from (1,1) to (3,5) of the gridpane. (column, row)
        double w = canvasAnchorPane.getWidth();
        double h = canvasAnchorPane.getHeight();
        return new double[] {w, h};
    }

    private void drawImageInCanvas(Image img){
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
    public void menuExit(ActionEvent event){
        Platform.exit();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }
}
