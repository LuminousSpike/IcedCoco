package com.limbo.icedcoco;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    private GridPane masterPane;

    @FXML
    private Button btnGeneral;

    @FXML
    private Button btnHotkeys;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnLoad;

    @FXML
    private Button btnDefaults;

    @FXML
    private Button btnExit;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu;

    @FXML
    private MenuItem menuItemSave;

    @FXML
    private MenuItem menuItemSaveAs;

    @FXML
    private MenuItem menuItemOpen;

    @FXML
    private MenuItem menuItemDefaults;

    @FXML
    private MenuItem menuItemExit;

    @FXML
    private StackPane settingsStackPane;

    @FXML
    private GridPane settingsGeneralPane;

    @FXML
    private GridPane settingsHotkeysPane;

    @FXML
    private GridPane settingsServerPane;

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

    @FXML
    private CheckBox startupShdOnStartupCheckbox;

    @FXML
    private CheckBox startupShdOnUpdateCheckbox;

    @FXML
    private ChoiceBox<SettingsInfo.OptionFrequency> startupReopenLastDirectoryChoicebox;

    @FXML
    private ChoiceBox<SettingsInfo.OptionFrequency> startupReopenLastFileChoicebox;

    @FXML
    private ChoiceBox<SettingsInfo.OptionFrequency> startupCheckForUpdatesChoiceBox;

    @FXML
    private CheckBox savingOnOpeningImageCheckbox;

    @FXML
    private TextField savingDefaultSaveLocationTextField;

    @FXML
    private ChoiceBox<SettingsInfo.OptionFrequency> savingAutomaticallyExportChoiceBox;

    @FXML
    private CheckBox savingDefaultSaveLocationCheckbox;

    private SettingsInfo settingsInfo;
    private HotkeysInfo hotkeysInfo;

    @FXML
    void OpenHelpWindow(ActionEvent event) {

    }

    @FXML
    private void loadFromFile() {
        System.err.println("loadFromFile has been called!");
    }

    @FXML
    private void saveToFile() {
        if (settingsStackPane.getChildren().get(0) == settingsGeneralPane) {
            saveGeneralSettings();
            settingsInfo.save("Settings.xml");
            showConfirmation("General Settings", "File Saved", "The Settings file has been saved and applied!");
        }
        else if (settingsStackPane.getChildren().get(0) == settingsHotkeysPane) {
            saveHotkeys();
            hotkeysInfo.save("Hotkeys.xml");
            showConfirmation("Hotkey Settings", "File Saved", "The Hotkeys file has been saved and applied!");
        }
    }

    @FXML
    private void saveToFileAs() {
        System.err.println("saveToFileAs has been called!");
    }

    @FXML
    private void loadDefaults() {
        if (settingsStackPane.getChildren().get(0) == settingsGeneralPane) {
            settingsInfo.loadDefault();
            loadGeneralSettings();
            showConfirmation("General Settings", "Defaults Applied", "The Settings default values have been applied!");
        }
        else if (settingsStackPane.getChildren().get(0) == settingsHotkeysPane) {
            hotkeysInfo.loadDefault();
            loadHotkeys();
            showConfirmation("Hotkey Settings", "Defaults Applied", "The Hotkeys default values have been applied!");
        }
    }

    @FXML
    private void exit() {
        // Weird hacky way that works.
        ((Stage)btnExit.getScene().getWindow()).close();
    }

    void setHotkeysInfo (HotkeysInfo hotkeysInfo) {
        this.hotkeysInfo = hotkeysInfo;
    }

    void setSettingsInfo (SettingsInfo settingsInfo) {
        this.settingsInfo = settingsInfo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        constrainTitlePanes();
        focusGeneralPane();
    }

    public void start() {
        // Populate the choice boxes
        startupReopenLastDirectoryChoicebox.getItems().setAll(SettingsInfo.OptionFrequency.values());
        startupReopenLastFileChoicebox.getItems().setAll(SettingsInfo.OptionFrequency.values());
        startupCheckForUpdatesChoiceBox.getItems().setAll(SettingsInfo.OptionFrequency.values());
        savingAutomaticallyExportChoiceBox.getItems().setAll(SettingsInfo.OptionFrequency.values());

        // Load the settings
        loadGeneralSettings();
        loadHotkeys();
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
    @FXML
    private void focusServerPane () {
        settingsStackPane.getChildren().clear();;
        settingsStackPane.getChildren().add(settingsServerPane);
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

    private boolean showNotSaved (String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get() == buttonYes);
    }

    private void showConfirmation (String titleText, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void loadGeneralSettings () {
        startupShdOnStartupCheckbox.setSelected(settingsInfo.showHelpDialogOnStartup);
        startupShdOnUpdateCheckbox.setSelected(settingsInfo.showHelpDialogOnUpdate);
        startupReopenLastDirectoryChoicebox.setValue(settingsInfo.reopenLastDirectory);
        startupReopenLastFileChoicebox.setValue(settingsInfo.reopenLastFile);
        startupCheckForUpdatesChoiceBox.setValue(settingsInfo.checkForUpdates);
        savingOnOpeningImageCheckbox.setSelected(settingsInfo.showMetaDataWarning);
        savingDefaultSaveLocationTextField.setText(settingsInfo.defaultSaveLocation);
        savingAutomaticallyExportChoiceBox.setValue(settingsInfo.automaticallyExport);
        savingDefaultSaveLocationCheckbox.setSelected(settingsInfo.useDefaultSaveLocation);
    }

    private void saveGeneralSettings () {
        settingsInfo.showHelpDialogOnStartup = startupShdOnStartupCheckbox.isSelected();
        settingsInfo.showHelpDialogOnUpdate = startupShdOnUpdateCheckbox.isSelected();
        settingsInfo.reopenLastDirectory = startupReopenLastDirectoryChoicebox.getValue();
        settingsInfo.reopenLastFile = startupReopenLastFileChoicebox.getValue();
        settingsInfo.checkForUpdates = startupCheckForUpdatesChoiceBox.getValue();
        settingsInfo.showMetaDataWarning = savingOnOpeningImageCheckbox.isSelected();
        settingsInfo.defaultSaveLocation = savingDefaultSaveLocationTextField.getText();
        settingsInfo.automaticallyExport = savingAutomaticallyExportChoiceBox.getValue();
        settingsInfo.useDefaultSaveLocation = savingDefaultSaveLocationCheckbox.isSelected();
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
