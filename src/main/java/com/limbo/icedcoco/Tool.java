package com.limbo.icedcoco;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface Tool {
    /* Not sure if this is a good design to
     * have a canvas tied to the interface like this.
     * */

    /**
     * Setter for the tool's canvas.
     * @param canvas The canvas that the tool will draw on.
     */
    void setCanvas (Canvas canvas);

    void setHotkeysInfo (HotkeysInfo hotkeysInfo);

     void onKeyPress (KeyEvent e);

    void onKeyReleasedListener(KeyEvent e);

    /**
     * Handles the tool logic for when the mouse enters a drag motion.
     * @param e Mouse Event for the tool to process.
     */
    void onDragEntered (MouseEvent e);

    /**
     * Handles the tool logic for when the mouse is clicked.
     * @param e Mouse Event for the tool to process.
     */
    void onMouseClicked (MouseEvent e);

    /**
     * Handles the tool logic for when the mouse is dragged.
     * @param e Mouse Event for the tool to process.
     */
    void onMouseDragged (MouseEvent e);

    /**
     * Handles the tool logic for when a mouse button is pressed.
     * @param e Mouse Event for the tool to process.
     */
    void onMousePressed (MouseEvent e);

    /**
     * Handles the tool logic for when a mouse button is released.
     * @param e Mouse Event for the tool to process.
     */
    void onMouseReleased (MouseEvent e);

    /**
     * Draws the tool's contents onto the set canvas.
     */
    void draw ();
}
