package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class PolygonTool implements Tool{
    private LinkedList<Polygon> polygons;
    private Canvas drawingCanvas;

    public PolygonTool() {
        polygons = new LinkedList<Polygon>();
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
        Vertex selectedVertex = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                if (polygons.get(polygons.size() - 1).size() > 2) {
                    polygons.add(new Polygon());
                }
                break;
            }
        }

        // TODO: Check if we really need this if statement.
        if (selectedVertex == null) {
            Polygon p = null;

            p = polygons.getLast();
            p.add(e.getX(), e.getY());

            draw();
        }
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Vertex selectedVertex  = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected()) != null) {
                selectedVertex.setAxisX(e.getX());
                selectedVertex.setAxisY(e.getY());
                draw();
            }
        }
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        Vertex selectedVertex = null;

        for (Polygon p : polygons) {
            if ((selectedVertex = p.findSelected())!= null) {
                selectedVertex.setSelected(false);
            }

            p.setVertexColor(Color.BLUE);

            if ((selectedVertex = p.find(e.getX(), e.getY())) != null) {
                selectedVertex.setColor(Color.RED);
                selectedVertex.setSelected(true);
                draw();
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent e) {

    }

    /* I'm not too sure about this as of yet.
     * It really depends on how we want to separate the tool modes.
     * Do we display them when you change to the tool?
     * Or is it displayed persistently?
     * */
    private void draw () {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();

        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for (Polygon p : polygons) {
            p.draw(gc);
        }
    }
}
