package com.limbo.icedcoco;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

class Polygon {
    public List<Vertex> points = new LinkedList<>();

    Polygon() {

    }

    /**
     * @return The count of the vertexes in the polygon.
     */
    int size() {
        return points.size();
    }

    /**
     * @param new_x X-pos of the new vertex.
     * @param new_y Y-pos of the new vertex.
     */
    void add(double new_x, double new_y) {
        points.add(new Vertex(new_x, new_y));
    }

    Vertex removeSelected() {
        Vertex v = null, p = null;
        for (int i = 0; i < size(); i++) {
            v = points.get(i);
            if (v.getSelected()) {
                p = remove(v);
                i--;
            }
        }

        return p;
    }

    void selectAll(boolean setValue) {
        for (Vertex v : points) {
            if (setValue == true) {
                v.setSelected(true);
                v.setColor(Color.RED);
            } else {
                v.setSelected(false);
                v.setColor(Color.BLUE);
            }
        }
    }

    /**
     * @param new_x X-pos to find.
     * @param new_y Y-pos to find.
     * @return The found vertex or null.
     */
    Vertex find(double new_x, double new_y) {
        for (Vertex v : points) {
            if (v.collision(new_x, new_y)) {
                return v;
            }
        }

        return null;
    }

    /**
     * Draws the polygon.
     *
     * @param gc The canvas to draw on.
     */
    void draw(GraphicsContext gc, double offset, Color color, double scale) {


        double[] xps = new double[size()];
        double[] yps = new double[size()];
        int[] xpoints = getXPoints();
        int[] ypoints = getYPoints();
        for(int i=0; i<size(); i++){
            xps[i] = (double) xpoints[i] * scale;
            yps[i] = (double) ypoints[i] * scale;
        }
        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.4);
        gc.setFill(color);
        gc.fillPolygon(xps, yps, size());

        for (int count = 0; count < points.size(); count++) {
            double SIZE = points.get(count).getSIZE();

            points.get(count).draw(gc, offset);
            if (points.get(count).getSelected())
                gc.setStroke(Color.ORANGE);
            else
                gc.setStroke(Color.BLACK);

            if ((count + 1) == points.size()) {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX() * offset, points.get(count).getAxisY() * offset, points.get(0).getAxisX() * offset, points.get(0).getAxisY() * offset);
            } else {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX() * offset, points.get(count).getAxisY() * offset, points.get(count + 1).getAxisX() * offset, points.get(count + 1).getAxisY() * offset);
            }
        }
    }


    /**
     * @param colour The colour to give the polygon's vertexes.
     */
    void setVertexColor(Color colour) {
        for (Vertex v : points) {
            v.setColor(colour);
        }
    }

    public Polygon clonePolygon()
    {
        Polygon returnPolygon = new Polygon();
        for (Vertex po: points) {
            returnPolygon.add(po.getAxisX(), po.getAxisY());
        }
      return returnPolygon;
    }

    /**
     * @return The selected vertex of the polygon.
     */
    Vertex popSelectedPoint() {
        for (Vertex v : points) {
            if (v.getSelected()) {
                return v;
            }
        }
        return null;
    }

    /**
     * @return Array of the x co-ordinates for every vertex
     */
    int[] getXPoints() {
        int[] out = new int[this.size()];
        int i = 0;
        for (Vertex v : points) {
            out[i] = (int) v.getAxisX();
            ++i;
        }
        return out;
    }

    /**
     * @return Array of the y co-ordinates for every vertex
     */
    int[] getYPoints() {
        int[] out = new int[this.size()];
        int i = 0;
        for (Vertex v : points) {
            out[i] = (int) v.getAxisY();
            ++i;
        }
        return out;
    }

    /**
     * @return The top point.
     */
    Vertex popPoint() {
        return points.get(points.size() - 1);
    }

    /**
     * @return All selected points.
     */
    LinkedList<Vertex> getSelectedPoints() {
        LinkedList<Vertex> selectedPoints = new LinkedList<Vertex>();
        for (Vertex v : points) {
            if (v.getSelected()) {
                selectedPoints.add(v);
            }
        }
        return selectedPoints;
    }

    /**
     * Adds either a selected vertex or a new vertex based on x and y coords.
     * @param x x-coord of new vertex.
     * @param y y-coord of new vertex.
     * @param selectedVertex Currently selected vertex.
     */
    public void add(double x, double y, Vertex selectedVertex) {
        if (selectedVertex == null) {
            add(x, y);
            return;
        }

        int index = points.indexOf(selectedVertex);
        points.add(index + 1, new Vertex(x, y));
    }

    /**
     * Remove the selected vertex.
     * @param selectedVertex Vertex to remove.
     * @return The removed vertex.
     */
    public Vertex remove(Vertex selectedVertex) {
        Vertex v = null;
        if (selectedVertex != null) {
            int index = Math.max(0, points.indexOf(selectedVertex) - 1);
            points.remove(selectedVertex);
            if (points.size() > 0)
                v = points.get(index);
        }
        return v;
    }

    /**
     * Converts points to JSON.
     * @return JSON formatted array of point coords.
     */
    public JSONArray getJSONArray(){
        // returns a JSONArray containing a JSONArray for each (x,y) point of the polygon, so [[x1,y1],[x2,y2]..]
        JSONArray out = new JSONArray();
        for(Vertex point : points){
            JSONArray entry = new JSONArray();
            entry.add(0, (int)point.getAxisX());
            entry.add(1, (int)point.getAxisY());
            out.add(entry);
        }
        return out;
    }
}
