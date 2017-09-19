package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class SelectTool implements Tool {
    private LinkedList<Polygon> polygons;
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


    public SelectTool(LinkedList<Polygon> new_Polygon) {
        polygons = new_Polygon;
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
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
    public void onMouseClicked(MouseEvent e) {
        Vertex selectedVertex = null;
        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                if (polygons.get(polygons.size() - 1).size() > 3) {
                    polygons.add(new Polygon());
                }
                break;
            }
        }
        if(drawSquare  == true)
        {
            for (Polygon p : polygons) {
                {

                }
            }
        }
        drawSquare = false;
        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Vertex selectedVertex  = null;
        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                for (Vertex v : p.points) {
                    if(v.getSelected() == true) {
                        v.setAxisX((v.getAxisX() - (offsetX/ scale - e.getX()/ scale)));
                        v.setAxisY((v.getAxisY() - (offsetY/ scale - e.getY()/ scale)) );
                    }
                }

            }
        }
        endX = e.getX() / scale;
        endY = e.getY() / scale;
        draw();
        offsetX = e.getX();
        offsetY = e.getY();

    }

    @Override
    public void onMousePressed(MouseEvent e) {
        offsetX = e.getX();
        offsetY = e.getY();
        if(e.isSecondaryButtonDown())
        {
            if (polygons.get(polygons.size() - 1).size() > 3)
            {
                polygons.add(new Polygon());

            }
            rightClick = true;
            for (Polygon p : polygons) {
                p.selectAll(false);
            }
        }
        else {
            Vertex selectedVertex = null;
            boolean onlyOne = false;
            for (Polygon p : polygons) {
                p.setVertexColor(Color.BLUE);


                p.setVertexColor(Color.BLUE);

                if ((selectedVertex = p.find(e.getX() / scale, e.getY() / scale)) != null && onlyOne == false) {

                    if(selectedVertex.getSelected() == true)
                    {
                        p.selectAll(true);
                    }
                    else
                    {
                        p.selectAll(false);
                    }
                    selectedVertex.setColor(Color.RED);
                    selectedVertex.setSelected(true);
                    onlyOne = true;
                }
                else
                {
                    p.selectAll(false);
                }
            }
            if (onlyOne == false) {
                startingX = e.getX() / scale;
                startingY = e.getY() / scale;
                endX = e.getX() / scale;
                endY = e.getY() / scale;
                drawSquare = true;
            }

        }
        draw();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    @Override
    public void draw() {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        gc.setLineDashOffset(0d);
        gc.setLineDashes(10d, 10d);
        gc.setStroke(Color.BLACK);
        if(drawSquare == true)
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
        for (Polygon p : polygons) {
            p.draw(gc,scale);
        }
    }
}