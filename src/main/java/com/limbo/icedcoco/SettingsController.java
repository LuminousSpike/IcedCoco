package com.limbo.icedcoco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable{
    @FXML
    private GridPane masterPane;

    @FXML
    private Button btnPolygon;

    @FXML
    private Button btnEllipse;

    @FXML
    private Button btnEllipse1;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem saveMenuItem;

    @FXML
    private Button btnExit;

    @FXML
    private TextField hotkeyGlobalZoomInPrimary;

    @FXML
    private TextField hotkeyGlobalZoomInAlt;

    @FXML
    private TextField hotkeyGlobalZoomOutPrimary;

    @FXML
    private TextField hotkeyGlobalZoomOutAlt;

    @FXML
    private TextField hotkeyGlobalSaveFilePrimary;

    @FXML
    private TextField hotkeyGlobalSaveFileAlt;

    @FXML
    private TextField hotkeyGlobalSaveFileAsAlt;

    @FXML
    private TextField hotkeyGlobalSaveFileAsPrimary;

    @FXML
    private TextField hotkeyGlobalExportMaskPrimary;

    @FXML
    private TextField hotkeyGlobalExportMaskAlt;

    @FXML
    private TitledPane hotkeyToolPolygonPrimary;

    @FXML
    private TextField hotkeyToolPolygonAlt;

    @FXML
    private TextField hotkeyToolEllipsePrimary;

    @FXML
    private TextField hotkeyToolEllipseAlt;

    @FXML
    private TextField hotkeyToolSelectPrimary;

    @FXML
    private TextField hotkeyToolSelectAlt;

    @FXML
    private TextField hotkeyToolMoveAlt;

    @FXML
    private TextField hotkeyToolMovePrimary;

    @FXML
    private TextField hotkeyPolygonCreatePrimary;

    @FXML
    private TextField hotkeyPolygonCreateAlt;

    @FXML
    private TextField hotkeyPolygonMutiSelectPrimary;

    @FXML
    private TextField hotkeyPolygonMultiSelectAlt;

    @FXML
    private TextField hotkeyPolygonDeleteSelectedVerticesPrimary;

    @FXML
    private TextField hotkeyPolygonDeleteSelectedVerticesAlt;

    @FXML
    private TextField hotkeyPolygonDeleteAllVerticesAlt;

    @FXML
    private TextField hotkeyPolygonDeselectAllVerticesPrimary;

    @FXML
    private TextField hotkeyEllipseCreatePrimary;

    @FXML
    private TextField hotkeyEllipseCreateAlt;

    @FXML
    private TextField hotkeyEllipseCreatePerfectPrimary;

    @FXML
    private TextField hotkeyEllipseCreatePerfectAlt;

    @FXML
    private TextField hotkeyEllipseMultiSelectPrimary;

    @FXML
    private TextField hotkeyEllipseMultiSelectAlt;

    @FXML
    private TextField hotkeyEllipseDeleteSelectedVerticesAlt;

    @FXML
    private TextField hotkeyEllipseDeleteSelectedVerticesPrimary;

    @FXML
    private TextField hotkeyEllipseDeselectAllVerticesAlt;

    @FXML
    private TextField hotkeyEllipseDeselectAllVerticesPrimary;

    @FXML
    private TextField hotkeySelectVerticesPrimary;

    @FXML
    private TextField hotkeySelectVerticesAlt;

    @FXML
    private TextField hotkeySelectMultiPrimary;

    @FXML
    private TextField hotkeySelectMultiAlt;

    @FXML
    private TextField hotkeySelectDeleteVerticesPrimary;

    @FXML
    private TextField hotkeySelectDeleteVerticesAlt;

    @FXML
    private TextField hotkeySelectDeselectAllVerticesPrimary;

    @FXML
    private TextField hotkeySelectAllVerticesAlt;

    @FXML
    private CheckBox startupShdOnStartupCheckbox;

    @FXML
    private CheckBox startupShdOnUpdateCheckbox;

    @FXML
    private ChoiceBox<?> startupReopenLastDirectoryChoicebox;

    @FXML
    private ChoiceBox<?> startupReopenLastFileChoicebox;

    @FXML
    private ChoiceBox<?> startupCheckForUpdatesChoiceBox;

    @FXML
    private CheckBox savingOnOpeningImageCheckbox;

    @FXML
    private Button savingDefaultSaveLocationButton;

    @FXML
    private TextField savingDefaultSaveLocationTextField;

    @FXML
    private ChoiceBox<?> savingAutomaticallyExportChoiceBox;

    @FXML
    private CheckBox savingDefaultSaveLocationCheckbox;
    private Scene scene;
    private ResourceBundle resources;


    public void start(){
        // Load the settings
    }

    public void setScene(Scene s){
        this.scene = s;
    }

    @FXML
    void OpenHelpWindow(ActionEvent event) {

    }

    @FXML
    void activateEllipseTool(ActionEvent event) {

    }

    @FXML
    void activatePolygonTool(ActionEvent event) {

    }

    @FXML
    void activateSelectTool(ActionEvent event) {

    }

    @FXML
    void createMetadataFiles(ActionEvent event) {

    }

    @FXML
    void exportSegmentation(ActionEvent event) {

    }

    @FXML
    void growCanvas(ActionEvent event) {

    }

    @FXML
    void menuExit(ActionEvent event) {

    }

    @FXML
    void menuOpenImage(ActionEvent event) {

    }

    @FXML
    void menuOpenImageWithCoco(ActionEvent event) {

    }

    @FXML
    void menuSaveData(ActionEvent event) {

    }

    @FXML
    void onKeyPressListener(KeyEvent event) {

    }

    @FXML
    void prepareFileMenu(ActionEvent event) {

    }

    @FXML
    void selectMetadataFiles(ActionEvent event) {

    }

    @FXML
    void shrinkCanvas(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }
}
