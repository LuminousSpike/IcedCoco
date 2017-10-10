package com.limbo.icedcoco;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by jorda on 21/08/2017.
 */
public class CreateMetadataController implements Initializable {

    private ResourceBundle resources;
    private SessionInfo sessionInfo;
    private Scene scene;
    private File baseDirectory = null;

    @FXML private TextField directoryTextField;
    @FXML private TextField baseNameTextField;
    @FXML private CheckBox imageToggle;
    @FXML private CheckBox annotationToggle;
    @FXML private CheckBox segmentationToggle;
    @FXML private CheckBox verticesToggle;
    @FXML private Label imageLabel;
    @FXML private Label annotationLabel;
    @FXML private Label segmentationLabel;
    @FXML private Label verticesLabel;

    public void setSessionInfo(SessionInfo s){
        this.sessionInfo = s;
    }

    public void setScene(Scene s){
        this.scene = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    @FXML public void chooseDirectory(ActionEvent event){
        // open a DirectoryChooser to choose a folder/directory to save the files in
        DirectoryChooser dc = new DirectoryChooser();
        File dir = dc.showDialog(scene.getWindow());
        if(dir!=null){
            directoryTextField.setText(dir.getAbsolutePath());
            baseDirectory = dir;
        }

    }

    @FXML
    public void onEditBaseName(Event event){
        String base = baseNameTextField.getText() + "_";
        imageLabel.setText(base + "images.json");
        annotationLabel.setText(base + "annotation.json");
        segmentationLabel.setText(base + "segmentation.json");
        verticesLabel.setText(base + "tool_vertices.json");
    }

    @FXML
    public void onCreateButton(ActionEvent event){
        createAllFiles(false);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Files created successfully");
        alert.showAndWait();
    }

    @FXML
    public void onCreateAndUse(ActionEvent event){
        createAllFiles(true);
        sessionInfo.checkImageMetadata();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Files created successfully and are now selected for use.");
        alert.showAndWait();
    }

    private String getEmptyImageDataText(){
        // return a string for the content of an empty image info file, instead of using the JSON tools
        String contributor = "COMP314 Project Tool User";
        LocalDateTime now = LocalDateTime.now();
        String year = Integer.toString(now.getYear());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); // note the format
        String datetime = now.toString();
        return "{\"info\": {\"description\": \"This is a dataset created with the COMP314 Project Tool\"," +
                "\"url\": \"\", \"version\": \"1.0\", \"year\": " + year + ", " +
                "\"contributor\": \"" + contributor + "\", " +
                "\"date_created\": \"" + datetime + "\"}, " +
                "\"images\": []}";
    }

    private void createAllFiles(boolean use){
        // for each toggle, if true, create a file using the base file name and the chosen directory.
        String emptyObject = "{}";     // mimic an empty JSONObject
        String emptyArray = "[]";

        if(baseDirectory==null){
            return;
        }
        if(baseNameTextField.getText().trim().equals("")){
            return;
        }

        try {
            if (imageToggle.isSelected()) {
                File img = createMetadataFile(imageLabel.getText(), getEmptyImageDataText());
                if(use){
                    sessionInfo.imageDataFile = img;
                }
            }
            if(annotationToggle.isSelected()){
                File ann = createMetadataFile(annotationLabel.getText(), emptyArray);
                if(use){
                    sessionInfo.annotationFile = ann;
                }
            }
            if(segmentationToggle.isSelected()){
                File seg = createMetadataFile(segmentationLabel.getText(), emptyArray);
                if(use){
                    sessionInfo.segmentationFile = seg;
                }
            }
            if(verticesToggle.isSelected()){
                File bound = createMetadataFile(verticesLabel.getText(), emptyArray);
                if(use){
                    sessionInfo.verticesFile = bound;
                }
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private File createMetadataFile(String fileName, String content) throws IOException{
        File newFile = new File(this.baseDirectory, fileName);
        PrintWriter writer = new PrintWriter(newFile);
        writer.write(content);
        writer.flush();
        writer.close();
        newFile.createNewFile();
        return newFile;
    }

    @FXML
    public void onCancelButton(ActionEvent event){
        ((Stage)scene.getWindow()).close();
    }

}
