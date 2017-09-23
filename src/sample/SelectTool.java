package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class SelectTool implements Tool {
    private PolyList polygons;
    private Canvas drawingCanvas;
    private double startingX;
    private double startingY;
    private double endX = 0;
    private double endY = 0;
    private double size = 50;
    private boolean selectOn = false;
    private boolean drawSquare = false;
    public double scale = 1;
    public double offsetX = 0;
    public double offsetY = 0;
    private Boolean rightClick = false;


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
        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        draw();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
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
    }
}