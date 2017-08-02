package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;

import javax.annotation.Resources;
import javax.swing.*;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private ResourceBundle resources;
    private SessionInfo sessionInfo = new SessionInfo();
    private Scene scene;

    @FXML private MenuBar menuBar;
    @FXML private MenuItem saveMenuItem;
    @FXML private Menu menu;
    @FXML private Button button;

    public void setScene(Scene scene){
        this.scene = scene;
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
