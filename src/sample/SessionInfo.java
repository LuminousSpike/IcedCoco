package sample;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by jorda on 3/08/2017.
 */
public class SessionInfo {

    public Stage primaryStage;
    public double imageWidth;
    public double imageHeight;
    public boolean imageLoaded;
    public Image baseImage;

    public File imageDataFile;
    public File annotationFile;
    public File segmentationFile;
    public File boundingBoxFile;

    public JSONArray imageJSON;
    public JSONArray annotationJSON;
    public JSONArray segmentationJSON;

    public boolean saveFilesReady(){
        // return true if there are files available and selected to save for the coco caption, segmentation, image info data, etc
        return imageDataFile!=null;
    }

    public void checkImageMetadata(){
        // metadata handling, called when a new image is loaded to the canvas, or the selected metadata files change.
        // do nothing if image null
        if (baseImage==null){
            return;
        }
        // case 1 : no image metadata file is selected.
        if(imageDataFile==null){
            // show pop up warning
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No COCO Metadata Files Selected");
            alert.setContentText("Any changes you make will not be saved until an image metadata file is selected.");
            alert.showAndWait();
        }
        // case 2 : metadata file selected, image is not indexed in the metadata file.
        else if (true){
            // pop up? to add this image to the metadata files
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Current Image Not Indexed");
            alert.setContentText("The currently open image is not indexed in the selected COCO image data file. No annotation data will be saved unless it is indexed." +
                    "\n\nWould you like to index the image?");

            ButtonType buttonYes = new ButtonType("Yes");
            ButtonType buttonNo = new ButtonType("No");

            alert.getButtonTypes().setAll(buttonYes, buttonNo);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonYes){
                // index the image in the current json image data json file, and close dialog
            } else if (result.get() == buttonNo) {
                // do not index the image. do nothing
            }
            else{
                // user closed the dialog
            }
        }
        // case 3 : metadata file selected, image is in the metadata file.
        else{
            // load the metadata info for this image for editing.
        }
    }

    public void overwriteMetadata(Stage stage){
        if(baseImage==null) return;

        String imageID = "";
        if(imageDataFile==null){
            // prompt warning - no file to save to?
        }
        else{
            // load the image ID to use in saving the other files.
        }
        // then for each metadata file
        if(annotationFile!=null){
            // overwrite annotation for this image
        }
        if(segmentationFile!=null){
            // overwrite segmentation for this image
        }
        if(boundingBoxFile!=null){
            // overwrite bounding box for this image
        }
    }
}
