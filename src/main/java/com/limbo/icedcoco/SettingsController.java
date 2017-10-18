package com.limbo.icedcoco;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private GridPane masterPane;

    @FXML
    private Button btnGeneral;

    @FXML
    private Button btnHotkeys;

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
    private StackPane settingsStackPane;

    @FXML
    private GridPane settingsGeneralPane;

    @FXML
    private TitledPane titledGlobalHotkeys;

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
    private TitledPane titledToolHotkeys;

    @FXML
    private TextField hotkeyToolPolygonPrimary;

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
    private TitledPane titledPolygonToolHotkeys;

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
    private TitledPane titledEllipseToolHotkeys;

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
    private TitledPane titledSelectToolHotkeys;

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
    private GridPane settingsHotkeysPane;

    @FXML
    private TitledPane titledStartup;

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
    private TitledPane titledDisplay;

    @FXML
    private TitledPane titledSaving;

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

    @FXML
    private TitledPane titledMisc;

    private ArrayList<TextField> textFields;

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
        constrainTitlePanes();
        focusGeneralPane();
        try {
            JAXBContext jc = JAXBContext.newInstance("com.limbo.icedcoco");
            SettingsInfo sInfo = new SettingsInfo();
            sInfo.defaultSaveLocation = "/How/Are/You/";
            sInfo.showHelpDialogOnStartup = false;
            sInfo.showHelpDialogOnUpdate = true;
            sInfo.showMetaDataWarning = true;
            sInfo.checkForUpdates = SettingsInfo.OptionFrequency.Always;
            sInfo.reopenLastFile = SettingsInfo.OptionFrequency.Ask;
            sInfo.reopenLastDirectory = SettingsInfo.OptionFrequency.Ask;
            sInfo.automaticallyExport = SettingsInfo.OptionFrequency.Never;

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(sInfo, System.out);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void focusGeneralPane () {
        settingsStackPane.getChildren().clear();
        settingsStackPane.getChildren().add(settingsGeneralPane);
    }

    @FXML
    private void focusHotkeysPane () {
        settingsStackPane.getChildren().clear();;
        settingsStackPane.getChildren().add(settingsHotkeysPane);
    }

    private void constrainTitlePanes () {
        titledDisplay.setCollapsible(false);
        titledEllipseToolHotkeys.setCollapsible(false);
        titledGlobalHotkeys.setCollapsible(false);
        titledMisc.setCollapsible(false);
        titledPolygonToolHotkeys.setCollapsible(false);
        titledSaving.setCollapsible(false);
        titledSelectToolHotkeys.setCollapsible(false);
        titledStartup.setCollapsible(false);
        titledToolHotkeys.setCollapsible(false);
    }

    @FXML
    private void getKeyCombination (KeyEvent event) {
        TextField source = (TextField)event.getSource();
        String keysDown = "";
        if (event.isShortcutDown())
            keysDown += "Shortcut+";
        if (event.isShiftDown())
            keysDown += "Shift+";
        if (event.getCode().isLetterKey()) {
            keysDown += event.getCode().toString();
            KeyCombination kc = KeyCombination.keyCombination(keysDown);
            source.setText(kc.getDisplayText());
        }
    }

    private ArrayList<TextField> getTextFields (Pane pane) {
        ArrayList<TextField> textFields = new ArrayList<TextField>();
        ArrayList<Node> nodes = getNodes(pane, new ArrayList<Node>());
        for (Node node : nodes) {
            if (node instanceof TextField)
                textFields.add((TextField)node);
        }

        return textFields;
    }

    private static <T extends Parent> ArrayList<Node> getNodes (T parent, ArrayList<Node> nodes) {
        ObservableList<Node> children = null;

        if (parent instanceof TitledPane) {
            Node content = ((TitledPane) parent).getContent();
            getNodes((Parent)content, nodes);
        }

        for (Node node : parent.getChildrenUnmodifiable()) {
           if (node instanceof Pane) {
           }
           if (node instanceof TextField) {
               nodes.add(node);
           }
           if (node instanceof Parent) {
               getNodes((Parent)node, nodes);
           }
        }

        return nodes;
    }
}
