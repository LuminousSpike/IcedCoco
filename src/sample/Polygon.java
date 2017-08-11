package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

class Polygon {
    private List<Vertex> points = new LinkedList<>();
    private boolean complete = false;

    Polygon ()
    {

    }

    int size ()
    {
        return points.size();
    }

    void add (double new_x, double new_y)
    {
        points.add(new Vertex(new_x, new_y));
    }

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

    void draw (GraphicsContext gc)
    {
        for (int count = 0; count < points.size(); count++)
        {
            double SIZE = points.get(count).getSIZE();

            points.get(count).draw(gc);
            gc.setStroke(Color.BLACK);

            if ((count + 1) == points.size())
            {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX()+SIZE/2, points.get(count).getAxisY()+SIZE/2, points.get(0).getAxisX()+SIZE/2, points.get(0).getAxisY()+SIZE/2);
            }
            else
            {
                // TODO: expand this for readability.
                gc.strokeLine(points.get(count).getAxisX()+SIZE/2, points.get(count).getAxisY()+SIZE/2, points.get(count + 1).getAxisX()+SIZE/2, points.get(count + 1).getAxisY()+SIZE/2);
            }
        }
    }
    void setVertexColor (Color colour)
    {
        for (Vertex v : points)
        {
            v.updateColor(colour);
        }
    }
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
}
