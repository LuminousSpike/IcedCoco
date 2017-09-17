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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by jorda on 3/08/2017.
 */
public class SessionInfo {

    public Stage primaryStage;
    public double imageWidth;
    public double imageHeight;
    public boolean imageLoaded;
    public Image baseImage;
    public File baseImageFile;  // the file the image was loaded from

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

    private boolean showNotIndexedDialog(){
        // prompt user to add the current image to the selected image metadata file.
        // return true if the user clicks the yes button, return false otherwise
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Current Image Not Indexed");
        alert.setContentText("The currently open image is not indexed in the selected COCO image data file. No annotation data will be saved unless it is indexed." +
                "\n\nWould you like to index the image?");

        ButtonType buttonYes = new ButtonType("Yes");
        ButtonType buttonNo = new ButtonType("No");
        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        return (result.get()==buttonYes);
    }

    private void showNoMetadataSelectedDialog(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("No COCO Metadata Files Selected");
        alert.setContentText("Any changes you make will not be saved until an image metadata file is selected.");
        alert.showAndWait();
    }

    private String getNextID(){
        // return an available ID for a new image to use, based on whats in the image metadata file
        // currently, IDs are local to each image metadata file.
        // this takes O(n^2) time, idk how to test against all the IDs faster.
        long i = 0;

        try {

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(imageDataFile));
            JSONObject jsonobj = (JSONObject) obj;

            JSONArray images = (JSONArray) jsonobj.get("images");
            // build a list of every ID in the file
            Iterator<JSONObject> iterator = images.iterator();
            ArrayList<String> idlist = new ArrayList<String>();
            while (iterator.hasNext()) {
                String id = (String) ((JSONObject) iterator.next()).get("id");
                idlist.add(id);
            }

            while (idlist.contains(Long.toString(i))) {
                // if this value of i is currently being used as an index. change i
                ++i;
            }
        }catch(FileNotFoundException fnfe){
            // show an alert to say the selected image metadata file can no longer be found.
        }catch(ParseException pe){
            // show an alert to say the selected image metadata file may be invalid / wrong format.
        }catch(IOException ioe){

        }

        return Long.toString(i);
    }

    private void indexCurrentImage(){
        // add the currently loaded image to the image metadata file with a new ID
        // create entries in the other selected metadata files for the image data, and save.
        String id = getNextID();
        String filename = baseImageFile.getName();
        int license = 0;  // idk how to specify this
        int width = (int) baseImage.getWidth();
        int height = (int) baseImage.getHeight();
        String dateCaptured = "";
        String cocoURL = "";

        // load the entire JSONObject from the image file, add to it, then write it to the file.

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
            showNoMetadataSelectedDialog();
        }
        // case 2 : metadata file selected, image is not indexed in the metadata file.
        else if (true){
            // pop up? to add this image to the metadata files
            boolean doIndex = showNotIndexedDialog();
            if (doIndex){
                // index the image in the current json image data json file, and any other selected coco files
                indexCurrentImage();
            }
            else{
                // user chose "No" button or closed the dialog, do not index the image, do nothing.
            }
        }
        // case 3 : metadata file selected, image is in the metadata file.
        else{
            // load the metadata info for this image, and show it in the editor for editing.
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
