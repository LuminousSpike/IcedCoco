package sample;

import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by jorda on 3/08/2017.
 */
public class SessionInfo {

    public double imageWidth;
    public double imageHeight;
    public boolean imageLoaded;
    public Image baseImage;

    public File imageDataFile;
    public File annotationFile;
    public File segmentationFile;
    public File boundingBoxFile;

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
        }
        // case 2 : metadata file selected, image is not indexed in the metadata file.
        else if (true){
            // pop up? to add this image to the metadata files
        }
        // case 3 : metadata file selected, image is in the metadata file.
        else{
            // load the metadata info for this image for editing.
        }
    }

    public void overwriteMetadata(){
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
