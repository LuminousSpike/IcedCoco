package sample;

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

    @Override
    public void setCanvas(Canvas canvas) {
        drawingCanvas = canvas;
    }

    @Override
    public void onKeyPress(KeyEvent e) {
        draw();
    }

    @Override
    public void onDragEntered(MouseEvent e) {
        draw();
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            if (!drawSquare)
                polygons.mouseDraggedVertex(e.getSceneX(), e.getSceneY());

            endX = e.getSceneX() * polygons.getScale();
            endY = e.getSceneY() * polygons.getScale();

            if (polygons.getSelectedVertex() == null && drawSquare)
                polygons.getVerticesInBounds(startingX, startingY, endX, endY);
        }

        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            polygons.polygonClickedSecondary();
            return;
        }

        polygons.setCurrentPolygon(polygons.checkCollision(e.getSceneX(), e.getSceneY()));

        drawSquare = !polygons.vertexClickedPrimary(e.getSceneX(), e.getSceneY(), e.getClickCount(), e.isControlDown());

        startingX = e.getSceneX() * polygons.getScale();
        startingY = e.getSceneY() * polygons.getScale();
        endX = e.getSceneX() * polygons.getScale();
        endY = e.getSceneY() * polygons.getScale();

        draw();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        drawSquare = false;

        draw();
    }

    @Override
    public void draw() {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setLineDashOffset(0d);
        gc.setLineDashes(10d, 10d);
        gc.setStroke(Color.BLACK);

        if(drawSquare)
        {
            //square
            gc.strokeLine(startingX, startingY, startingX, endY);
            gc.strokeLine(startingX, startingY, endX, startingY);
            gc.strokeLine(startingX, endY, endX, endY);
            gc.strokeLine(endX, startingY, endX, endY);
            gc.setLineDashOffset(10d);
            gc.setStroke(Color.WHITE);
            gc.strokeLine(startingX, startingY, startingX, endY);
            gc.strokeLine(startingX, startingY, endX, startingY);
            gc.strokeLine(startingX, endY, endX, endY);
            gc.strokeLine(endX, startingY, endX, endY);
        }

        gc.setLineDashes(null);

        polygons.draw(gc);
    }
}