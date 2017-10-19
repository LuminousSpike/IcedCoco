package com.limbo.icedcoco;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EllipseTool implements Tool {
    private PolyList polygons;
    private Canvas drawingCanvas;
    private double startingX = 0;
    private double startingY = 0;
    private double endX = 0;
    private double endY = 0;
    private double size = 50;
    private boolean drawSquare = true;

    public EllipseTool(PolyList polyList) {
        polygons = polyList;
    }

    public void setSize(double size) {
        this.size = Math.max(size, 0);
        this.size = Math.min(this.size, 200);
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
     * Handles the onDragEntered event.
     * Redraws all the polygons.
     *
     * @param e MouseEvent.
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
     * the values for the preview ellipse.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMouseDragged(MouseEvent e) {
        if (!drawSquare)
            polygons.mouseDraggedVertex(e.getSceneX(), e.getSceneY());
        else if (e.isPrimaryButtonDown() && polygons.getSelectedVertex() == null) {
            double eX = e.getSceneX();
            double eY = e.getSceneY();

            if (e.isShiftDown()) {
                double tempX = eX - startingX;
                double tempY = eY - startingY;
                if (Math.abs(tempX) > Math.abs(tempY)) {
                    if (tempY < 0) {
                        tempY = -Math.abs(tempX);
                    } else {
                        tempY = Math.abs(tempX);
                    }
                } else {
                    if (tempX < 0) {
                        tempX = -Math.abs(tempY);
                    } else {
                        tempX = Math.abs(tempY);
                    }
                }
                endX = tempX + startingX;
                endY = tempY + startingY;
            } else {
                endX = eX;
                endY = eY;
            }
        }

        draw();
    }

    /**
     * Handles the onMousePressed event.
     * Redraws all the polygon and sets
     * the values for the beginning of the ellipse preview.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            polygons.polygonClickedSecondary();
            return;
        }

        if (e.isPrimaryButtonDown()) {
            polygons.setCurrentPolygon(polygons.checkCollision(e.getSceneX(), e.getSceneY()));

            drawSquare = !polygons.vertexClickedPrimary(e.getSceneX(), e.getSceneY(), e.getClickCount(), e.isShiftDown(), e.isControlDown());

            startingX = e.getSceneX();
            startingY = e.getSceneY();
            endX = e.getSceneX();
            endY = e.getSceneY();
        }

        draw();
    }

    /**
     * Handles the onMouseReleased event.
     * Redraws all the polygons and stops drawing
     * the preview ellipse box.
     * Also added the new ellipse.
     *
     * @param e MouseEvent.
     */
    @Override
    public void onMouseReleased(MouseEvent e) {
        Polygon p = new Polygon();

        if (drawSquare && ((startingX + 10 < endX || startingX - 10 > endX) && (startingY + 10 < endY || startingY - 10 > endY))) {
            for (double i = 0; i < size; i++) {
                double t = (360 / size) * i;
                double newX = (startingX + ((endX - startingX) / 2)) + ((endX - startingX) / 2) * Math.cos(t * Math.PI / 180.0);
                double newY = (startingY + ((endY - startingY) / 2)) + ((endY - startingY) / 2) * Math.sin(t * Math.PI / 180.0);
                p.add(newX, newY);
            }
            drawSquare = false;

            polygons.add(p);
        }
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
            //circle
            gc.strokeOval(Math.min(startingX, endX) * polygons.getScale(), Math.min(startingY, endY) * polygons.getScale(),
                    (Math.max(startingX, endX) - Math.min(startingX, endX)) * polygons.getScale(), (Math.max(startingY, endY) - Math.min(startingY, endY)) * polygons.getScale());
            gc.setLineDashOffset(10d);
            gc.setStroke(Color.WHITE);
            gc.strokeOval(Math.min(startingX, endX) * polygons.getScale(), Math.min(startingY, endY) * polygons.getScale(),
                    (Math.max(startingX, endX) - Math.min(startingX, endX)) * polygons.getScale(), (Math.max(startingY, endY) - Math.min(startingY, endY)) * polygons.getScale());
        }

        gc.setLineDashes(null);

        polygons.draw(gc);
    }
}
