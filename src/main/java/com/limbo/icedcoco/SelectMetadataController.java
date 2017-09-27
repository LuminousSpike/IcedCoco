package com.limbo.icedcoco;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by jorda on 22/08/2017.
 */
public class SelectMetadataController implements Initializable{

    private ResourceBundle resources;
    private SessionInfo sessionInfo;
    private Scene scene;
    private String noneSelectedText = "No File Selected";

    @FXML private TextField imageTextField;
    @FXML private TextField segmentationTextField;
    @FXML private TextField annotationTextField;
    @FXML private TextField boundingBoxTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    public void start(){
        // set the textfields to show the current selected files, if there are any selected
        initialiseFileText(imageTextField, sessionInfo.imageDataFile);
        initialiseFileText(segmentationTextField, sessionInfo.segmentationFile);
        initialiseFileText(annotationTextField, sessionInfo.annotationFile);
        initialiseFileText(boundingBoxTextField, sessionInfo.boundingBoxFile);
    }

    private void initialiseFileText(TextField textField, File file){
        if(file==null){
            textField.setText(noneSelectedText);
        }
        else{
            textField.setText(file.getAbsolutePath());
        }
    }

    public void setSessionInfo(SessionInfo s){
        this.sessionInfo = s;
    }

    public void setScene(Scene s){
        this.scene = s;
    }

    private File chooseFile(){
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Coco Metadata", "*.json", "*.JSON");
        FileChooser.ExtensionFilter allFilter = new FileChooser.ExtensionFilter("All Files", "*.*");
        fc.getExtensionFilters().add(imgFilter);
        fc.getExtensionFilters().add(allFilter);
        File dataFile = fc.showOpenDialog(scene.getWindow());
        return dataFile;
    }

    @FXML
    public void chooseImageFile(ActionEvent event){
        File f = chooseFile();
        if(f==null){
            return;
        }
        imageTextField.setText(f.getAbsolutePath());
        sessionInfo.imageDataFile = f;
    }

    @FXML
    public void chooseSegmentationFile(ActionEvent event){
        File f = chooseFile();
        if(f==null){
            return;
        }
        segmentationTextField.setText(f.getAbsolutePath());
        sessionInfo.segmentationFile = f;
    }

    @FXML
    public void chooseAnnotationFile(ActionEvent event){
        File f = chooseFile();
        if(f==null){
            return;
        }
        annotationTextField.setText(f.getAbsolutePath());
        sessionInfo.annotationFile = f;
    }

    @FXML
    public void chooseBoundingBoxFile(ActionEvent event){
        File f = chooseFile();
        if(f==null){
            return;
        }
        boundingBoxTextField.setText(f.getAbsolutePath());
        sessionInfo.boundingBoxFile = f;
    }

    @FXML
    public void onDoneButton(ActionEvent event){
        sessionInfo.checkImageMetadata();
        ((Stage)scene.getWindow()).close();
    }

    @FXML
    public void onCancelButton(ActionEvent event){
        ((Stage)scene.getWindow()).close();
    }

    @FXML
    public void onNoImage(ActionEvent event){
        imageTextField.setText(noneSelectedText);
        sessionInfo.imageDataFile = null;
    }

    @FXML
    public void onNoSegmentation(ActionEvent event){
        segmentationTextField.setText(noneSelectedText);
        sessionInfo.segmentationFile = null;
    }

    @FXML
    public void onNoAnnotation(ActionEvent event){
        annotationTextField.setText(noneSelectedText);
        sessionInfo.annotationFile = null;
    }

    @FXML
    public void onNoBounding(ActionEvent event){
        boundingBoxTextField.setText(noneSelectedText);
        sessionInfo.boundingBoxFile = null;
    }

}
