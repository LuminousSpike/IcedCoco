package com.limbo.icedcoco;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SelectTool implements Tool {
    private PolyList polygons;
    private Canvas drawingCanvas;
    private double startingX;
    private double startingY;
    private double endX = 0;
    private double endY = 0;
    private boolean drawSquare = false;

    public SelectTool(PolyList new_Polygon) {
        polygons = new_Polygon;
    }

    /**
     * Sets the canvas to draw on.
     *
     * @param canvas the canvas to set.
     */
    @Override
    public void setCanvas(Canvas canvas) {
        drawingCanvas = canvas;
    }

    /**
     * Handles the onKeyPress event.
     * Redraws all the polygons.
     *
     * @param e KeyEvent.
     */
    @Override
    public void onKeyPress(KeyEvent e) {
        draw();
    }

    /**
     * Handles the onDragEntered event.
     * Redraws all the polygons.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onDragEntered(MouseEvent e) {
        draw();
    }

    /**
     * Handles the onMouseClicked event.
     * Redraws all the polygon.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMouseClicked(MouseEvent e) {
        draw();
    }

    /**
     * Handles the onMouseDragged event.
     * Redraws all the polygon and sets
     * the end coords for the select box.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMouseDragged(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            if (!drawSquare)
                polygons.mouseDraggedVertex(e.getSceneX(), e.getSceneY());

            endX = e.getSceneX();
            endY = e.getSceneY();

            if (polygons.getSelectedVertex() == null && drawSquare)
                polygons.getVerticesInBounds(startingX, startingY, endX, endY);
        }

        draw();
    }

    /**
     * Handles the onMousePressed event.
     * Redraws all the polygon and sets
     * the values for the beginning of the selection box.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            polygons.polygonClickedSecondary();
            return;
        }

        polygons.setCurrentPolygon(polygons.checkCollision(e.getSceneX(), e.getSceneY()));

        drawSquare = !polygons.vertexClickedPrimary(e.getSceneX(), e.getSceneY(), e.getClickCount(), e.isShiftDown(), e.isControlDown());

        startingX = e.getSceneX();
        startingY = e.getSceneY();
        endX = e.getSceneX();
        endY = e.getSceneY();

        draw();
    }

    /**
     * Handles the onMouseReleased event.
     * Redraws all the polygons and stops drawing
     * the selection box.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMouseReleased(MouseEvent e) {
        drawSquare = false;

        draw();
    }

    /**
     * Draws the tool's contents onto the set canvas.
     */
    @Override
    public void draw() {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setLineDashOffset(0d);
        gc.setLineDashes(10d, 10d);
        gc.setStroke(Color.BLACK);

        if (drawSquare) {
            //square
            double scaledStartingX = this.startingX * polygons.getScale();
            double scaledStartingY = this.startingY * polygons.getScale();
            double scaledEndY = this.endY * polygons.getScale();
            double scaledEndX = this.endX * polygons.getScale();

            gc.strokeLine(scaledStartingX, scaledStartingY, scaledStartingX, scaledEndY);
            gc.strokeLine(scaledStartingX, scaledStartingY, scaledEndX, scaledStartingY);
            gc.strokeLine(scaledStartingX, scaledEndY, scaledEndX, scaledEndY);
            gc.strokeLine(scaledEndX, scaledStartingY, scaledEndX, scaledEndY);
            gc.setLineDashOffset(10d);
            gc.setStroke(Color.WHITE);
            gc.strokeLine(scaledStartingX, scaledStartingY, scaledStartingX, scaledEndY);
            gc.strokeLine(scaledStartingX, scaledStartingY, scaledEndX, scaledStartingY);
            gc.strokeLine(scaledStartingX, scaledEndY, scaledEndX, scaledEndY);
            gc.strokeLine(scaledEndX, scaledStartingY, scaledEndX, scaledEndY);
        }

        gc.setLineDashes(null);

        polygons.draw(gc);
    }
}