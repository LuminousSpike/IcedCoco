package main.java.com.limbo.icedcoco;

import javafx.scene.canvas.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class SessionInfo {

    public javafx.scene.canvas.Canvas canvas;
    public TextArea captionTextArea;
    public double imageWidth;
    public double imageHeight;
    public Image baseImage;
    public File baseImageFile;  // the file the image was loaded from
    public PolyList polygons = new PolyList();

    public File imageDataFile;
    public File annotationFile;
    public File segmentationFile;
    public File verticesFile;

    public long currentImageID = 0;

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
        // also stores the ID from the image in currentImageID
        // these values are ordered here as they should appear in the JSONObject
        long id = getNextID();
        currentImageID = id;
        JSONObject newImage = new JSONObject();
        newImage.put("license", 0);     // not sure what the license values are
        newImage.put("file_name", baseImageFile.getName());
        newImage.put("coco_url", "");
        newImage.put("height", (int) baseImage.getHeight());
        newImage.put("width", (int) baseImage.getWidth());
        newImage.put("id", id);
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
        // return true if the current image in the editor is indexed in the selected image metadata file.
        // when returning true, also sets currentImageID.
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
                    currentImageID = (long) img.get("id");
                    break;
                }
            }

        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(ParseException pe){
            pe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return output;
    }

    public void checkImageMetadata(){
        // metadata handling, called when a new image is loaded to the canvas, or the selected metadata files change.
        // do nothing if image is null
        if (baseImageFile==null){
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
            loadCurrentCaption();
            loadVertices();
        }
    }

    public void overwriteMetadata(Stage stage){
        if(baseImage==null) return;

        if(imageDataFile==null){
            // prompt warning - no file to save to?
        }
        else{
            // load the image ID to use in saving the other files.
        }
        // then for each metadata file
        if(annotationFile!=null){
            // overwrite annotation for this image
            overwriteAnnotation(captionTextArea.getText());
        }
        if(segmentationFile!=null){
            // overwrite segmentation for this image
            overwriteSegmentation();
        }
        if(verticesFile!=null){
            // overwrite the segmentation vertices for this image
            overwriteVertices();
        }
    }

    private String getRLEString(){
        // return a string that is the run length encoding of the segmentation image, as per mscoco
        String rle = "";
        BufferedImage img = getSegmentationImage();

        // image/binary mask of it, => RLE object
        // then use rleToString(rle)

        return rle;
    }

    public BufferedImage getSegmentationImage(){
        // returns an indexed BufferedImage, where 0=not a segment(black), 1=segmented (pink)
        int width = (int)this.imageWidth;
        int height = (int)this.imageHeight;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_INDEXED);

        Graphics2D gfx = img.createGraphics();
        // turn off anti aliasing, segmentation PNG should be pixel perfect
        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        Color segmentColor = Color.pink;
        // set everything to be black
        gfx.setBackground(Color.black);
        // convert all of our own Polygon instances to java.awt.Polygon instances, and draw them to the image, filled.
        gfx.setColor(segmentColor);
        for (Polygon p : polygons.getPolygons()) {
            if(p.size() < 4) {continue;}
            int[] xpoints = p.getXPoints();
            int[] ypoints = p.getYPoints();
            for (int i=0; i<p.size(); i++){
                xpoints[i] = (int) (xpoints[i] * width / canvas.getWidth());
                ypoints[i] = (int) (ypoints[i] * height / canvas.getHeight());
            }
            java.awt.Polygon awtPoly = new java.awt.Polygon(xpoints, ypoints, p.size());
            gfx.fillPolygon(awtPoly);
            gfx.drawPolygon(awtPoly);
        }

        return img;
    }

    private void overwriteVertices(){
        // the "verticesArray" is a JSONArray containing JSONObjects. each JSONObject has an int ID, and a JSONArray "polylist" containing polygon points.
        if(verticesFile==null){return;}
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader(verticesFile);
            JSONArray verticesArray = (JSONArray) parser.parse(reader);
            reader.close();

            boolean contains = false;
            Iterator<JSONObject> iterator = verticesArray.iterator();
            JSONObject entry = null;
            // loop until reach end of array or find the entry for the current image
            while (iterator.hasNext() && !contains) {
                entry = iterator.next();
                String id = Long.toString((long) entry.get("id"));
                // if the current image is in the array, remove it
                if(id.equals(Long.toString(currentImageID))){
                    verticesArray.remove(entry);
                    contains = true;
                }
            }
            // insert the data about the vertices into the array
            JSONArray polyArray = polygons.getJSONArray();
            JSONObject newEntry = new JSONObject();
            newEntry.put("polylist", polyArray);
            newEntry.put("id", currentImageID);
            verticesArray.add(newEntry);

            // write to the file
            FileWriter writer = new FileWriter(verticesFile);
            writer.write(verticesArray.toJSONString());
            writer.flush();
            writer.close();

        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(ParseException pe){
            pe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void overwriteSegmentation(){
        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(segmentationFile);
            JSONArray arr = (JSONArray) parser.parse(reader);
            reader.close();

            boolean contains = false;
            Iterator<JSONObject> iterator = arr.iterator();
            JSONObject entry = null;
            // loop until reach end of array or find the entry for the current image
            while (iterator.hasNext() && !contains) {
                entry = iterator.next();
                String id = Long.toString((long) entry.get("id"));
                // if the current image is in the array, remove it
                if(id.equals(Long.toString(currentImageID))){
                    arr.remove(entry);
                    contains = true;
                }
            }
            // insert the new segmentation entry into the array
            JSONObject newEntry = new JSONObject();
            newEntry.put("id", currentImageID);
            newEntry.put("category_id", 0);
            newEntry.put("score", 0);
            JSONObject seg = new JSONObject();
            JSONArray sizeArray = new JSONArray();
            sizeArray.add(0, (int)baseImage.getWidth());
            sizeArray.add(1, (int)baseImage.getHeight());
            seg.put("size", sizeArray);
            String rle = getRLEString();
            seg.put("counts", rle);
            newEntry.put("segmentation", seg);
            arr.add(newEntry);

            // write to the file
            FileWriter writer = new FileWriter(segmentationFile);
            writer.write(arr.toJSONString());
            writer.flush();
            writer.close();
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch(ParseException pe){
            pe.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void loadVertices(){
        // check for vertices file, and for the current image assign the vertice values to the PolyList polygons
        if(verticesFile==null){return;}
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader(verticesFile);
            JSONArray verticeArray = (JSONArray) parser.parse(reader);
            reader.close();

            Iterator<JSONObject> iterator = verticeArray.iterator();
            while (iterator.hasNext()) {
                // read the ids as longs, then add them to the list as strings, to use String equality checking instead of long.
                JSONObject obj = iterator.next();
                String id = Long.toString((long) obj.get("id"));
                if(id.equals(Long.toString(currentImageID))){
                    this.polygons = new PolyList((JSONArray)obj.get("polylist"));
                    return;
                }
            }
            // falling out of the iterator loop means there is no exisitng polygon data for this image, so use an empty PolyList
            this.polygons = new PolyList();

        }catch(FileNotFoundException fnfe){

        }catch(ParseException pe){

        }catch(IOException ioe){

        }
    }

    private void loadCurrentCaption(){
        // if an annotation file is selected, search for a caption for the current image ID, and display it.
        captionTextArea.setText("");
        if(annotationFile==null){return;}
        JSONParser parser = new JSONParser();
        try{
            FileReader reader = new FileReader(annotationFile);
            JSONArray captions = (JSONArray) parser.parse(reader);
            reader.close();

            Iterator<JSONObject> iterator = captions.iterator();
            while (iterator.hasNext()) {
                // read the ids as longs, then add them to the list as strings, to use String equality checking instead of long.
                JSONObject obj = iterator.next();
                String id = Long.toString((long) obj.get("id"));
                if(id.equals(Long.toString(currentImageID))){
                    String caption = (String)obj.get("caption");
                    captionTextArea.setText(caption);
                    return;
                }
            }
            // falling out of the iterator loop means there is no caption for this image, so clear the text area.
            captionTextArea.setText("");

        }catch(FileNotFoundException fnfe){

        }catch(ParseException pe){

        }catch(IOException ioe){

        }
    }

    private void overwriteAnnotation(String caption){
        // can assume annotationFile is not null
        // save the current annotation text to the caption file
        // read the file, change/insert the caption, save the file

        JSONParser parser = new JSONParser();
        try {
            FileReader reader = new FileReader(annotationFile);
            JSONArray arr = (JSONArray) parser.parse(reader);
            reader.close();

            boolean contains = false;
            Iterator<JSONObject> iterator = arr.iterator();
            JSONObject entry = null;
            // loop until reach end of array or find the entry for the current image
            while (iterator.hasNext() && !contains) {
                entry = iterator.next();
                String id = Long.toString((long) entry.get("id"));
                // if the current image is in the array, remove it
                if(id.equals(Long.toString(currentImageID))){
                    arr.remove(entry);
                    contains = true;
                }
            }
            // insert the caption into the array
            JSONObject newEntry = new JSONObject();
            newEntry.put("id", currentImageID);
            newEntry.put("caption", caption);
            arr.add(newEntry);

            // write to the file
            FileWriter writer = new FileWriter(annotationFile);
            writer.write(arr.toJSONString());
            writer.flush();
            writer.close();

        }catch(FileNotFoundException fnfe){

        }catch(ParseException pe){

        }catch(IOException ioe){

        }

    }
}
