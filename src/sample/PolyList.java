package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;

public class PolyList {
    private LinkedList<Polygon> polygons, polygons_selected;
    private LinkedList<Vertex> vertices_selected;
    private Vertex selectedVertex;
    private boolean onlyOne;
    private double scale;

    public void setScale (double scale) {
        this.scale = scale;
    }

    public  PolyList () {
        polygons = new LinkedList<Polygon>();
        polygons_selected = new LinkedList<Polygon>();
        vertices_selected = new LinkedList<Vertex>();
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
}
