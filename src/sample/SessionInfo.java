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

import java.io.*;
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

    private long getNextID(){
        // return an available ID for a new image to use, based on whats in the image metadata file
        // currently, IDs are local to each image metadata file.
        // this takes O(n^2) time, idk how to test against all the IDs faster.
        long i = 0;

        try {
            FileReader reader = new FileReader((imageDataFile));
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            JSONObject jsonobj = (JSONObject) obj;

            JSONArray images = (JSONArray) jsonobj.get("images");
            // build a list of every ID in the file
            Iterator<JSONObject> iterator = images.iterator();
            ArrayList<String> idlist = new ArrayList<String>();
            while (iterator.hasNext()) {
                // read the ids as longs, then add them to the list as strings, to use String equality checking instead of long.
                String id = Long.toString((long) ((JSONObject) iterator.next()).get("id"));
                idlist.add(id);
            }

            while (idlist.contains(Long.toString(i))) {
                // if this value of i is currently being used as an index. change i
                ++i;
            }
            reader.close();
        }catch(FileNotFoundException fnfe){
            // show an alert to say the selected image metadata file can no longer be found.
        }catch(ParseException pe){
            // show an alert to say the selected image metadata file may be invalid / wrong format.
        }catch(IOException ioe){

        }

        return i;
    }

    private void indexCurrentImage(){
        // add the currently loaded image to the image metadata file with a new ID
        // create entries in the other selected metadata files for the image data, and save.
        // these values are ordered here as they should appear in the JSONObject

        JSONObject newImage = new JSONObject();
        newImage.put("license", 0);     // not sure what the license values are
        newImage.put("file_name", baseImageFile.getName());
        newImage.put("coco_url", "");
        newImage.put("height", (int) baseImage.getHeight());
        newImage.put("width", (int) baseImage.getWidth());
        newImage.put("id", getNextID());
        newImage.put("date_captured", ""); // need external library for grabbing image metadata from file - do later.

        // load the entire JSONObject from the image file, add to it, then write it back to the file.
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader(imageDataFile);
            JSONObject masterObject = (JSONObject) parser.parse(reader);
            reader.close();

            JSONArray images = (JSONArray) masterObject.get("images");
            images.add(newImage);

            FileWriter writer = new FileWriter(imageDataFile);
            masterObject.put("images", images);
            writer.write(masterObject.toJSONString());
            writer.flush();
            writer.close();

        }catch(FileNotFoundException fnfe){

        }catch(ParseException pe){

        }catch(IOException ioe){

        }
    }

    private boolean currentImageIndexed(){
        // return true if the current image in the editor is indexed in the selected image metadata file
        // assume baseImage, imageDataFile are not null
        boolean output = false;

        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(imageDataFile);
            JSONObject object = (JSONObject) parser.parse(reader);
            reader.close();
            JSONArray images = (JSONArray) object.get("images");
            String currentImageName = baseImageFile.getName();

            // check each image. If the file_name attributes match, our image is indexed, return true.
            // otherwise falls out and returns false.
            Iterator<JSONObject> iterator = images.iterator();
            while (iterator.hasNext()) {
                JSONObject img = iterator.next();
                String name = (String) img.get("file_name");
                if(name.equals(currentImageName)){
                    output = true;
                    break;
                }
            }

        }catch(FileNotFoundException fnfe){

        }catch(ParseException pe){

        }catch(IOException ioe){

        }
        return output;
    }


    public void checkImageMetadata(){
        // metadata handling, called when a new image is loaded to the canvas, or the selected metadata files change.
        // do nothing if image is null
        if (baseImage==null){
            return;
        }
        // case 1 : no image metadata file is selected.
        if(imageDataFile==null){
            // show pop up warning
            showNoMetadataSelectedDialog();
            return;
        }
        // case 2 : metadata file selected, image is not indexed in the metadata file.
        else if (!currentImageIndexed()){
            // show a pop up prompt to add this image to the metadata files
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
