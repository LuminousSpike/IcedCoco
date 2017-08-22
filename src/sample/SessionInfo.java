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
        return false;
    }
}
