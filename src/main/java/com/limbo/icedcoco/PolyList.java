package com.limbo.icedcoco;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

public class PolyList {
    private LinkedList<Polygon> polygons;
    private Polygon currentPolygon;
    private LinkedList<Vertex> selectedVertices;
    private Vertex selectedVertex;
    private double scale = 1, previousMousePosX = 0, previousMousePosY = 0;
    private Color[] colorList = new Color[] {Color.HOTPINK, Color.BLUE, Color.GREEN, Color.PURPLE, Color.BEIGE, Color.ORANGE, Color.CORNSILK, Color.YELLOW, Color.AQUAMARINE, Color.SANDYBROWN};

    public PolyList() {
        polygons = new LinkedList<Polygon>();
        selectedVertices = new LinkedList<Vertex>();
    }

    public PolyList(JSONArray arr){
        // create a polylist using a json array containing JSONArrays of polygons
        // the polygons themselves are JSONArrays of vertice positions, like [[x1,y1], [x2,y2],..]
        polygons = new LinkedList<>();
        Iterator<JSONArray> polygonIterator = arr.iterator();
        // for every polygon, represented as a JSONArray
        while (polygonIterator.hasNext()) {
            JSONArray polygonArray = polygonIterator.next();
            Polygon p = new Polygon();
            // for every vertex, represented as a JSONArray
            Iterator<JSONArray> vertexIterator = polygonArray.iterator();
            while(vertexIterator.hasNext()){
                // add that vertex to the new Polygon object
                JSONArray v = vertexIterator.next();
                p.add((double)((long)v.get(0)), (double)((long)v.get(1)));
            }
            // add that Polygon object to the PolyList
            polygons.add(p);
        }

        selectedVertices = new LinkedList<>();

    }

    private void addSelectedVertex(Vertex selectedVertex) {
        if (selectedVertex == null)
            return;

        if (this.selectedVertex != null)
            this.selectedVertex.setSelected(false);

        selectedVertex.setSelected(true);

        if (!selectedVertices.contains(selectedVertex))
            selectedVertices.add(selectedVertex);
    }

    private void removeSelectedVertex(Vertex selectedVertex) {
        if (this.selectedVertex != null)
            this.selectedVertex.setSelected(false);

        if (selectedVertex != null)
            selectedVertex.setSelected(false);

        selectedVertices.remove(selectedVertex);
    }

    public Polygon getCurrentPolygon() {
        return currentPolygon;
    }

    public void setCurrentPolygon(Polygon currentPolygon) {
        this.currentPolygon = currentPolygon;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Vertex getSelectedVertex() {
        return selectedVertex;
    }

    public void setSelectedVertex(Vertex selectedVertex) {
        if (this.selectedVertex != null && !selectedVertices.contains(this.selectedVertex))
            this.selectedVertex.setSelected(false);

        if (selectedVertex != null)
            selectedVertex.setSelected(true);

        this.selectedVertex = selectedVertex;
    }

    public LinkedList<Polygon> getPolygons() {
        return polygons;
    }

    public void add(Polygon polygon) {
        polygons.add(polygon);
    }

    public Polygon createPoly() {
        Polygon polygon = new Polygon();
        polygons.add(polygon);

        return polygon;
    }

    public Polygon createPoly(double x, double y) {
        Polygon polygon = createPoly();
        polygon.add(x, y);

        return polygon;
    }

    public Polygon checkCollision(double x, double y) {
        Vertex v = null;

        for (Polygon polygon : polygons) {
            v = polygon.find(x, y);

            if (v != null)
                return polygon;
        }

        return null;
    }

    public void draw(GraphicsContext gc) {
        int i = 0;
        for (Polygon p : polygons) {
            p.draw(gc, scale, colorList[i % colorList.length], scale);
            ++i;
        }
    }

    public void remove(Polygon polygon) {
        polygons.remove(polygon);
    }

    public void remove(Vertex vertex) {
        for (int i = polygons.size() - 1; i >= 0; i--) {
            Polygon p = polygons.get(i);
            if (currentPolygon == p)
                setSelectedVertex(p.removeSelected());
            else
                p.removeSelected();

            if (p.size() < 3) {
                polygons.remove(p);
                if (currentPolygon == p)
                    currentPolygon = null;
            }
        }
    }

    public void polygonClickedSecondary() {
        if (getCurrentPolygon() != null)
            if (getCurrentPolygon().size() < 3)
                remove(getCurrentPolygon());

        setSelectedVertex(null);
        setCurrentPolygon(null);

        clearSelectedVertices();
    }

    private void clearSelectedVertices() {
        int count = selectedVertices.size();
        for (int i = 0; i < count; i++)
            removeSelectedVertex(selectedVertices.peek());
    }

    public boolean vertexClickedPrimary(double x, double y, int clickCount, boolean isShiftDown, boolean controlDown) {
        setPreviousMousePos(x, y);

        if (getCurrentPolygon() == null) {
            setSelectedVertex(null);

            if (!isShiftDown)
                clearSelectedVertices();

            return false;
        }

        if (clickCount == 2) {
            boolean added = false;

            for (Vertex v : currentPolygon.points) {
                if (!selectedVertices.contains(v)) {
                    addSelectedVertex(v);
                    added = true;
                }
            }

            if (!added) {
                for (Vertex v : currentPolygon.points) {
                    removeSelectedVertex(v);
                }

                setSelectedVertex(null);
                setCurrentPolygon(null);

                return false;
            }
        }

        if (isShiftDown) {
            addSelectedVertex(getSelectedVertex());
        }

        setSelectedVertex(getCurrentPolygon().find(x, y));

        if (getSelectedVertex() == null)
            setSelectedVertex(getCurrentPolygon().popSelectedPoint());

        if (controlDown) {
            removeSelectedVertex(getSelectedVertex());

            if (selectedVertices.size() > 0)
                setSelectedVertex(selectedVertices.getLast());

            if (getSelectedVertex() == null)
                setCurrentPolygon(null);
        }

        return true;
    }

    public void mouseDraggedVertex(double x, double y) {
        if (getSelectedVertex() == null) {
            return;
        }

        if (selectedVertices.size() > 0) {
            for (Vertex v : selectedVertices) {
                v.translateX(x - previousMousePosX);
                v.translateY(y - previousMousePosY);
            }
        }

        if (!selectedVertices.contains(getSelectedVertex())) {
            getSelectedVertex().setAxisX(x);
            getSelectedVertex().setAxisY(y);
        }

        setPreviousMousePos(x, y);
    }

    public void setPreviousMousePos(double x, double y) {
        previousMousePosX = x;
        previousMousePosY = y;
    }

    public void getVerticesInBounds(double startingX, double startingY, double endX, double endY) {
        // Handles all orientations of the bounding box!
        double x1 = Math.min(startingX, endX);
        double x2 = Math.max(startingX, endX);
        double y1 = Math.min(startingY, endY);
        double y2 = Math.max(startingY, endY);

        for (Polygon p : polygons) {
            // Really not good for performance
            for (Vertex v : p.points) {
                double vX = v.getAxisX();
                double vY = v.getAxisY();

                if (x1 <= vX && vX <= x2 && y1 <= vY && vY <= y2) {
                    addSelectedVertex(v);
                } else {
                    removeSelectedVertex(v);
                }
            }
        }
    }

    public void addPointToPoly(double x, double y, Vertex selectedVertex) {
        clearSelectedVertices();
        getCurrentPolygon().add(x, y, selectedVertex);
    }

    public JSONArray getJSONArray(){
        // returns a JSONArray containing JSONArrays representing each polygon in the poly list
        JSONArray out = new JSONArray();
        for(Polygon p : polygons){
            out.add(p.getJSONArray());
        }
        return out;
    }
}
