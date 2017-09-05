package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class EllipseTool implements Tool{
    private LinkedList<Polygon> polygons;
    private Canvas drawingCanvas;
    private double startingX;
    private double startingY;
    private double endX =0;
    private double endY =0;
    private double size = 50;
    private boolean drawSquare = false;


    public EllipseTool(LinkedList<Polygon> new_Polygon) {
        polygons = new_Polygon;
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
    }

    public EllipseTool(LinkedList<Polygon> new_Polygon, int new_Size) {
        polygons = new_Polygon;
        size = new_Size;
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
    }

    @Override
    public void setCanvas(Canvas canvas) {
        drawingCanvas = canvas;
    }

    @Override
    public void onDragEntered(MouseEvent e) {
        Vertex v = null;

        for (Polygon p : polygons) {
            p.setVertexColor (Color.BLUE);

            if ((v = p.find(e.getX(), e.getY())) != null) {
                v.setColor(Color.RED);
            }
        }

        if (v != null) {
            v.setAxisX(e.getX());
            v.setAxisY(e.getY());
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e) {
        drawSquare = false;
        if((startingX +10 < endX || startingX -10 > endX)&&(startingY +10 < endY ||startingY +10 > endY)) {
            for (double i = 0; i < size; i++) {
                double t = (360 / size) * i;
                double newX = (startingX + ((endX - startingX) / 2)) + ((endX - startingX) / 2) * Math.cos(t * Math.PI / 180.0);
                double newY = (startingY + ((endY - startingY) / 2)) + ((endY - startingY) / 2) * Math.sin(t * Math.PI / 180.0);
                Polygon p = null;
                p = polygons.getLast();
                p.add(newX, newY);
            }
            polygons.add(new Polygon());
        }
        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        endX = e.getX();
        endY = e.getY();
        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        startingX = e.getX();
        startingY = e.getY();
        endX = e.getX();
        endY = e.getY();
        drawSquare = true;
        draw();
}

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    /* I'm not too sure about this as of yet.
     * It really depends on how we want to separate the tool modes.
     * Do we display them when you change to the tool?
     * Or is it displayed persistently?
     * */
    @Override
    public void draw () {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        if(drawSquare == true)
        {
            gc.strokeLine(startingX, startingY, startingX, endY);
            gc.strokeLine(startingX, startingY, endX, startingY);
            gc.strokeLine(startingX, endY, endX, endY);
            gc.strokeLine(endX, startingY, endX, endY);
        }
        for (Polygon p : polygons) {
            p.draw(gc);
        }
    }
}
