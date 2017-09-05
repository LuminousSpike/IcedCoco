package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.awt.*;
import java.util.LinkedList;

public class EllipseTool implements Tool{
    private LinkedList<Polygon> polygons;
    private Canvas drawingCanvas;
    private double startingX;
    private double startingY;
    private double endX =0;
    private double endY =0;
    private double size = 50;
    private boolean selectOn = false;
    private boolean drawSquare = false;
    public double scale = 1;


    public EllipseTool(LinkedList<Polygon> new_Polygon) {
        polygons = new_Polygon;
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
    }

    public EllipseTool(LinkedList<Polygon> new_Polygon, int new_Size) {
        polygons = new_Polygon;
        size = new_Size;
        if(new_Size < 4)
        {
            size = 4;
        }
        if(new_Size > 200)
        {
            size = 200;
        }
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

            if ((v = p.find(e.getX()/scale, e.getY()/scale)) != null) {
                v.setColor(Color.RED);
            }
        }

        if (v != null) {
            v.setAxisX(e.getX()/scale);
            v.setAxisY(e.getY()/scale);
        }
    }

    @Override
    public void onMouseClicked(MouseEvent e)
    {
        if(selectOn ==false && ((startingX + 10 < endX || startingX - 10 > endX) && (startingY + 10 < endY || startingY - 10 > endY)))
        {
            drawSquare = false;
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
        selectOn = false;
        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {

        Vertex selectedVertex  = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                selectedVertex.setAxisX(e.getX()/scale);
                selectedVertex.setAxisY(e.getY()/scale);
                draw();
            }
        }

        endX = e.getX()/scale;
        endY = e.getY()/scale;
        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {

        polygons.add(new Polygon());
        Vertex selectedVertex = null;
        boolean onlyOne = false;
        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected())!= null) {
                selectedVertex.setSelected(false);
            }

            p.setVertexColor(Color.BLUE);

            if ((selectedVertex = p.find(e.getX()/scale, e.getY()/scale)) != null && onlyOne == false) {
                selectedVertex.setColor(Color.RED);
                selectedVertex.setSelected(true);
                onlyOne = true;
                selectOn = true;
            }
        }
        if(onlyOne == false) {
            startingX = e.getX()/scale;
            startingY = e.getY()/scale;
            endX = e.getX()/scale;
            endY = e.getY()/scale;
            drawSquare = true;
        }
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
        gc.setLineDashOffset(0d);
        gc.setLineDashes(10d, 10d);
        gc.setStroke(Color.BLACK);
        if(drawSquare == true)
        {
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
        for (Polygon p : polygons) {
            p.draw(gc,scale);
        }

    }
}
