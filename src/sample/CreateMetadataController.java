package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.image.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.InputMethodEvent;
import java.awt.im.spi.InputMethod;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
    @FXML private CheckBox boundingToggle;
    @FXML private Label imageLabel;
    @FXML private Label annotationLabel;
    @FXML private Label segmentationLabel;
    @FXML private Label boundingLabel;

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
        boundingLabel.setText(base + "bounding.json");
    }

    @FXML
    public void onCreateButton(ActionEvent event){
        // for each toggle, if true, create a file using the base file name and the chosen directory.
        String dummyContent = "boop";

        if(baseDirectory==null){
            return;
        }
        if(baseNameTextField.getText().trim().equals("")){
            return;
        }

        try {
            if (imageToggle.isSelected()) {
                createMetadataFile(imageLabel.getText(), dummyContent);
            }
            if(annotationToggle.isSelected()){
                createMetadataFile(annotationLabel.getText(), dummyContent);
            }
            if(segmentationToggle.isSelected()){
                createMetadataFile(segmentationLabel.getText(), dummyContent);
            }
            if(boundingToggle.isSelected()){
                createMetadataFile(boundingLabel.getText(), dummyContent);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }


        ((Stage)scene.getWindow()).close();
    }

    private void createMetadataFile(String fileName, String content) throws IOException{
        File newFile = new File(this.baseDirectory, fileName);
        PrintWriter writer = new PrintWriter(newFile);
        writer.write(content);
        writer.flush();
        writer.close();
        newFile.createNewFile();
    }

    @FXML
    public void onCancelButton(ActionEvent event){
        ((Stage)scene.getWindow()).close();
    }

}
