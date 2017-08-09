package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public interface Tool {
    /* Not sure if this is a good design to
     * have a canvas tied to the interface like this.
     * */
    void setCanvas (Canvas canvas);
    void onDragEntered (MouseEvent e);
    void onMouseClicked (MouseEvent e);
    void onMouseDragged (MouseEvent e);
    void onMousePressed (MouseEvent e);
    void onMouseReleased (MouseEvent e);
}
