package sample;

/**
 * Created by jorda on 3/08/2017.
 */
public class SessionInfo {

    public int imageWidth;
    public int imageHeight;
    public boolean imageLoaded;

    public boolean saveFilesReady(){
        // return true if there are files available and selected to save for the coco caption, segmentation, image info data, etc
        return false;
    }
}
