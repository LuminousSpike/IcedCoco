package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

class Polygon {
    private List<Vertex> points = new LinkedList<>();
    private double offset = 1;

    Polygon ()
    {

    }

    /**
     * @return The count of the vertexes in the polygon.
     */
    int size ()
    {
        return points.size();
    }

    /**
     * @param new_x X-pos of the new vertex.
     * @param new_y Y-pos of the new vertex.
     */
    void add (double new_x, double new_y)
    {
        points.add(new Vertex(new_x, new_y));
    }

    /**
     * @param new_x X-pos to find.
     * @param new_y Y-pos to find.
     * @return The found vertex or null.
     */
    Vertex find (double new_x, double new_y)
    {
        for (Vertex v : points)
        {
            if (v.collision(new_x, new_y))
            {
                return v;
            }
        }

        return null;
    }

    /**
     * Draws the polygon.
     * @param gc The canvas to draw on.
     */
    void draw (GraphicsContext gc, double offset)
    {
        for (int count = 0; count < points.size(); count++)
        {
            double SIZE = points.get(count).getSIZE();

            points.get(count).draw(gc,offset);
            gc.setStroke(Color.BLACK);

            if ((count + 1) == points.size())
            {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX()*offset, points.get(count).getAxisY()*offset, points.get(0).getAxisX()*offset, points.get(0).getAxisY()*offset);
            }
            else
            {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX()*offset, points.get(count).getAxisY()*offset, points.get(count + 1).getAxisX()*offset, points.get(count + 1).getAxisY()*offset);
            }
        }
    }


    /**
     * @param colour The colour to give the polygon's vertexes.
     */
    void setVertexColor (Color colour)
    {
        for (Vertex v : points)
        {
            v.setColor(colour);
        }
    }


    /**
     * @return The selected vertex of the polygon.
     */
    Vertex findSelected ()
    {
        for (Vertex v : points)
        {
            if(v.getSelected())
            {
                return v;
            }
        }
        return null;
    }

    /**
     *  @return Array of the x co-ordinates for every vertex
     */
    int[] getXPoints(){
        int[] out = new int[this.size()];
        int i = 0;
        for(Vertex v : points){
            out[i] = (int)v.getAxisX();
            ++i;
        }
        return out;
    }

    /**
     *  @return Array of the y co-ordinates for every vertex
     */
    int[] getYPoints(){
        int[] out = new int[this.size()];
        int i = 0;
        for(Vertex v : points){
            out[i] = (int)v.getAxisY();
            ++i;
        }
        return out;
    }




}
