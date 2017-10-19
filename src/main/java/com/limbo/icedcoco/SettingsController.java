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
    private GridPane settingsHotkeysPane;

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
    private TitledPane titledDisplay;

    @FXML
    private TitledPane titledStartup;

    @FXML
    private TitledPane titledMisc;

    @FXML
    private TitledPane titledSaving;

    @FXML
    private TitledPane titledToolActionHotkeys;
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
    private GridPane hotkeyActionPrimarySecondary;

    @FXML
    private TextField hotkeyActionPrimaryPrimary;

    @FXML
    private TextField hotkeyActionPrimaryAlt;

    @FXML
    private TextField hotkeyActionSecondaryPrimary;

    @FXML
    private TextField hotkeyActionSecondaryAlt;

    @FXML
    private TextField hotkeyActionModifierPrimary;

    @FXML
    private TextField hotkeyActionModifierAlt;

    @FXML
    private TextField hotkeyActionUndoAlt;

    @FXML
    private TextField hotkeyActionUndoPrimary;

    private ArrayList<TextField> textFields;

    private HotkeysInfo hotkeysInfo;

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
        titledToolActionHotkeys.setCollapsible(false);
        titledGlobalHotkeys.setCollapsible(false);
        titledMisc.setCollapsible(false);
        titledSaving.setCollapsible(false);
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

    private void loadHotkeys () {
        hotkeyGlobalZoomInPrimary.setText(hotkeysInfo.GlobalZoomInPrimary.getDisplayText());
        hotkeyGlobalZoomInAlt.setText(hotkeysInfo.GlobalZoomInAlt.getDisplayText());
        hotkeyGlobalZoomOutPrimary.setText(hotkeysInfo.GlobalZoomOutPrimary.getDisplayText());
        hotkeyGlobalZoomOutAlt.setText(hotkeysInfo.GlobalZoomOutAlt.getDisplayText());
        hotkeyGlobalSaveFilePrimary.setText(hotkeysInfo.GlobalSaveFilePrimary.getDisplayText());
        hotkeyGlobalSaveFileAlt.setText(hotkeysInfo.GlobalSaveFileAlt.getDisplayText());
        hotkeyGlobalSaveFileAsPrimary.setText(hotkeysInfo.GlobalSaveFileAsPrimary.getDisplayText());
        hotkeyGlobalSaveFileAsAlt.setText(hotkeysInfo.GlobalSaveFileAsAlt.getDisplayText());
        hotkeyGlobalExportMaskPrimary.setText(hotkeysInfo.GlobalExportMaskPrimary.getDisplayText());
        hotkeyGlobalExportMaskAlt.setText(hotkeysInfo.GlobalExportMaskAlt.getDisplayText());
        hotkeyToolPolygonPrimary.setText(hotkeysInfo.ToolPolygonPrimary.getDisplayText());
        hotkeyToolPolygonAlt.setText(hotkeysInfo.ToolPolygonAlt.getDisplayText());
        hotkeyToolEllipsePrimary.setText(hotkeysInfo.ToolEllipsePrimary.getDisplayText());
        hotkeyToolEllipseAlt.setText(hotkeysInfo.ToolEllipseAlt.getDisplayText());
        hotkeyToolSelectPrimary.setText(hotkeysInfo.ToolSelectPrimary.getDisplayText());
        hotkeyToolSelectAlt.setText(hotkeysInfo.ToolSelectAlt.getDisplayText());
        hotkeyToolMovePrimary.setText(hotkeysInfo.ToolMovePrimary.getDisplayText());
        hotkeyToolMoveAlt.setText(hotkeysInfo.ToolMoveAlt.getDisplayText());
        hotkeyActionPrimaryPrimary.setText(hotkeysInfo.ActionPrimaryPrimary.getDisplayText());
        hotkeyActionPrimaryAlt.setText(hotkeysInfo.ActionPrimaryAlt.getDisplayText());
        hotkeyActionSecondaryPrimary.setText(hotkeysInfo.ActionSecondaryPrimary.getDisplayText());
        hotkeyActionSecondaryAlt.setText(hotkeysInfo.ActionSecondaryAlt.getDisplayText());
        hotkeyActionModifierPrimary.setText(hotkeysInfo.ActionModifierPrimary.getDisplayText());
        hotkeyActionModifierAlt.setText(hotkeysInfo.ActionModifierAlt.getDisplayText());
        hotkeyActionUndoPrimary.setText(hotkeysInfo.ActionUndoPrimary.getDisplayText());
        hotkeyActionUndoAlt.setText(hotkeysInfo.ActionUndoAlt.getDisplayText());
    }

    private void saveHotkeys() {
        hotkeysInfo.GlobalZoomInPrimary = KeyCombination.keyCombination(hotkeyGlobalZoomInPrimary.getText());
        hotkeysInfo.GlobalZoomInAlt = KeyCombination.keyCombination(hotkeyGlobalZoomInAlt.getText());
        hotkeysInfo.GlobalZoomOutPrimary = KeyCombination.keyCombination(hotkeyGlobalZoomOutPrimary.getText());
        hotkeysInfo.GlobalZoomOutAlt = KeyCombination.keyCombination(hotkeyGlobalZoomOutAlt.getText());
        hotkeysInfo.GlobalSaveFilePrimary = KeyCombination.keyCombination(hotkeyGlobalSaveFilePrimary.getText());
        hotkeysInfo.GlobalSaveFileAlt = KeyCombination.keyCombination(hotkeyGlobalSaveFileAlt.getText());
        hotkeysInfo.GlobalSaveFileAsPrimary = KeyCombination.keyCombination(hotkeyGlobalSaveFileAsPrimary.getText());
        hotkeysInfo.GlobalSaveFileAsAlt = KeyCombination.keyCombination(hotkeyGlobalSaveFileAsAlt.getText());
        hotkeysInfo.GlobalExportMaskPrimary = KeyCombination.keyCombination(hotkeyGlobalExportMaskPrimary.getText());
        hotkeysInfo.GlobalExportMaskAlt = KeyCombination.keyCombination(hotkeyGlobalExportMaskAlt.getText());
        hotkeysInfo.ToolPolygonPrimary = KeyCombination.keyCombination(hotkeyToolPolygonPrimary.getText());
        hotkeysInfo.ToolPolygonAlt = KeyCombination.keyCombination(hotkeyToolPolygonAlt.getText());
        hotkeysInfo.ToolEllipsePrimary = KeyCombination.keyCombination(hotkeyToolEllipsePrimary.getText());
        hotkeysInfo.ToolEllipseAlt = KeyCombination.keyCombination(hotkeyToolEllipseAlt.getText());
        hotkeysInfo.ToolSelectPrimary = KeyCombination.keyCombination(hotkeyToolSelectPrimary.getText());
        hotkeysInfo.ToolSelectAlt = KeyCombination.keyCombination(hotkeyToolSelectAlt.getText());
        hotkeysInfo.ToolMovePrimary = KeyCombination.keyCombination(hotkeyToolMovePrimary.getText());
        hotkeysInfo.ToolMoveAlt = KeyCombination.keyCombination(hotkeyToolMoveAlt.getText());
        hotkeysInfo.ActionPrimaryPrimary = KeyCombination.keyCombination(hotkeyActionPrimaryPrimary.getText());
        hotkeysInfo.ActionPrimaryAlt = KeyCombination.keyCombination(hotkeyActionPrimaryAlt.getText());
        hotkeysInfo.ActionSecondaryPrimary = KeyCombination.keyCombination(hotkeyActionSecondaryPrimary.getText());
        hotkeysInfo.ActionSecondaryAlt = KeyCombination.keyCombination(hotkeyActionSecondaryAlt.getText());
        hotkeysInfo.ActionModifierPrimary = KeyCombination.keyCombination(hotkeyActionModifierPrimary.getText());
        hotkeysInfo.ActionModifierAlt = KeyCombination.keyCombination(hotkeyActionModifierAlt.getText());
        hotkeysInfo.ActionUndoPrimary = KeyCombination.keyCombination(hotkeyActionUndoPrimary.getText());
        hotkeysInfo.ActionUndoAlt = KeyCombination.keyCombination(hotkeyActionUndoAlt.getText());
    }
}
