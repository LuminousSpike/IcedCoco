package sample;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

public class PolyList {
    private LinkedList<Polygon> polygons;
    private Polygon currentPolygon;
    private LinkedList<Vertex> selectedVertices;
    private Vertex selectedVertex;
    private double scale = 1, previousMousePosX = 0, previousMousePosY = 0;

    public void setScale (double scale) {
        this.scale = scale;
    }
    public void setCurrentPolygon(Polygon currentPolygon) {
        this.currentPolygon = currentPolygon;
    }

    public void setSelectedVertex (Vertex selectedVertex) {
        if (this.selectedVertex != null && !selectedVertices.contains(this.selectedVertex))
            this.selectedVertex.setSelected(false);

        if (selectedVertex != null)
            selectedVertex.setSelected(true);

        this.selectedVertex = selectedVertex;
    }

    private void addSelectedVertex (Vertex selectedVertex) {
        if (selectedVertex == null)
            return;

        if (this.selectedVertex != null)
            this.selectedVertex.setSelected(false);

        selectedVertex.setSelected(true);

        if (!selectedVertices.contains(selectedVertex))
            selectedVertices.add(selectedVertex);
    }

    private void removeSelectedVertex (Vertex selectedVertex) {
        if (this.selectedVertex != null)
            this.selectedVertex.setSelected(false);

        if (selectedVertex != null)
            selectedVertex.setSelected(false);

        selectedVertices.remove(selectedVertex);
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

    public void polygonClickedSecondary () {
        if (getCurrentPolygon() != null)
            if (getCurrentPolygon().size() < 3)
                remove(getCurrentPolygon());

        setSelectedVertex(null);
        setCurrentPolygon(null);

        clearSelectedVertices();
    }

    private void clearSelectedVertices () {
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

    public void setPreviousMousePos (double x, double y) {
        previousMousePosX = x;
        previousMousePosY = y;
    }

    public void getVerticesInBounds (double startingX, double startingY, double endX, double endY) {
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
                }
                else {
                    removeSelectedVertex(v);
                }
            }
        }
    }

    public void addPointToPoly(double x, double y, Vertex selectedVertex) {
        clearSelectedVertices();
        getCurrentPolygon().add(x, y, selectedVertex);
    }
}
