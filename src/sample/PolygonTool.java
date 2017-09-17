package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class PolygonTool implements Tool{
    private LinkedList<Polygon> polygons;
    private Canvas drawingCanvas;
    private double startingWidth;
    private double startingHeight;
    public double scale = 1;
    public double offsetX = 0;
    public double offsetY = 0;
    private Boolean rightClick = false;

    public PolygonTool(LinkedList<Polygon> new_Polygon) {
        polygons = new_Polygon;
        // TODO: Refactor this out. (Fixes polygons not being added somehow)
        polygons.add(new Polygon());
    }

    @Override
    public void setCanvas(Canvas canvas) {
        drawingCanvas = canvas;
        startingWidth = canvas.getWidth();
        startingHeight = canvas.getHeight();
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
        if(rightClick == true)
        {
            rightClick = false;
        }
        else {
            for (Polygon p : polygons) {
                if ((selectedVertex = p.findSelected()) != null) {
                    if (polygons.get(polygons.size() - 1).size() > 3) {
                        polygons.add(new Polygon());
                    }
                    break;
                }
            }

            // TODO: Check if we really need this if statement.
            if (selectedVertex == null) {
                Polygon p = null;

                p = polygons.getLast();
                p.add(e.getX() / scale, e.getY() / scale);
            }

        }
        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Vertex selectedVertex  = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                for (Vertex v : p.points) {
                    if(v.getSelected() == true) {
                        v.setAxisX((v.getAxisX() - (offsetX - e.getX())) / scale);
                        v.setAxisY((v.getAxisY() - (offsetY - e.getY())) / scale);
                    }
                }

            }
        }
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
                    selectedVertex.setColor(Color.RED);
                    if(selectedVertex.getSelected() == true)
                    {
                        p.selectAll(true);
                    }
                    else
                    {
                        //p.selectAll(false);
                    }
                    selectedVertex.setSelected(true);
                    onlyOne = true;
                }
                else
                {
                    p.selectAll(false);
                }
            }

        }
        draw();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    public void onKeyPress(KeyEvent e)
    {
        System.out.println("yes");
        if(e.getCode() == KeyCode.DELETE)
        {
            for (Polygon p : polygons) {
                p.deleteAllSelected();

            }
        }
        draw();
    }

    /* I'm not too sure about this as of yet.
     * It really depends on how we want to separate the tool modes.
     * Do we display them when you change to the tool?
     * Or is it displayed persistently?
     * */
    @Override
    public void draw () {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();

        for (Polygon p : polygons) {
            p.draw(gc,scale);
        }
    }
}
