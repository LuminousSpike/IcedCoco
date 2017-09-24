package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class PolyList {
    private LinkedList<Polygon> polygons;
    private Polygon currentPolygon;
    private LinkedList<Vertex> selectedVertices;
    private Vertex selectedVertex;
    private double scale = 1;

    public void setScale (double scale) {
        this.scale = scale;
    }
    public void setCurrentPolygon(Polygon currentPolygon) {
        this.currentPolygon = currentPolygon;
    }

    public void setSelectedVertex (Vertex selectedVertex) {
        if (this.selectedVertex != null)
            this.selectedVertex.setSelected(false);

        if (selectedVertex != null)
            selectedVertex.setSelected(true);

        this.selectedVertex = selectedVertex;
    }

    public Polygon getCurrentPolygon() {
        return currentPolygon;
    }

    public double getScale() {
        return scale;
    }

    public Vertex getSelectedVertex() {
        return selectedVertex;
    }

    public  PolyList () {
        polygons = new LinkedList<Polygon>();
        selectedVertices = new LinkedList<Vertex>();
    }

    public void add (Polygon polygon) {
        polygons.add(polygon);
    }

    public Polygon createPoly () {
        Polygon polygon = new Polygon();
        polygons.add(polygon);

        return polygon;
    }

    public Polygon createPoly (double x, double y) {
        Polygon polygon = createPoly();
        polygon.add(x, y);

        return polygon;
    }

    public Polygon checkCollision (double x, double y) {
        Vertex v = null;

        for(Polygon polygon : polygons) {
            v = polygon.find(x, y);

            if (v != null)
                return  polygon;
        }

        return null;
    }

    public void draw(GraphicsContext gc) {
        for (Polygon p : polygons) {
            p.draw(gc, scale);
        }
    }

    public void remove (Polygon polygon) {
        polygons.remove(polygon);
    }

    public void remove (Vertex vertex) {
        if (currentPolygon != null) {
            setSelectedVertex(currentPolygon.remove(selectedVertex));

            if (currentPolygon.size() < 3) {
                polygons.remove(currentPolygon);
                currentPolygon = null;
            }
        }
    }

    public void polygonClickedSecondary () {
        if (getCurrentPolygon() != null)
            if (getCurrentPolygon().size() < 3)
                remove(getCurrentPolygon());

        setSelectedVertex(null);
        setCurrentPolygon(null);
    }


    public boolean vertexClickedPrimary (double x, double y) {
        if (getCurrentPolygon() == null) {
            setSelectedVertex(null);
            return false;
        }

        setSelectedVertex(getCurrentPolygon().find(x, y));

        if (getSelectedVertex() == null)
            setSelectedVertex(getCurrentPolygon().popSelectedPoint());

        return true;
    }

    public void mouseDraggedVertex(double x, double y) {
        if (getSelectedVertex() == null) {
            return;
        }

        getSelectedVertex().setAxisX(x);
        getSelectedVertex().setAxisY(y);
    }
}
