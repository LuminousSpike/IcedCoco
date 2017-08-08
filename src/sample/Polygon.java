package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;

public class Polygon {
    private List<Vertex> points = new LinkedList<>();
    private boolean complete = false;


    public Polygon()
    {

    }
    public int size()
    {
        return points.size();
    }


    public void add(float new_x, float new_y)
    {
        points.add(new Vertex(new_x, new_y));
    }

    public Vertex find(float new_x, float new_y)
    {
        for (Vertex v:points)
        {
            if(v.collision(new_x, new_y) == true)
            {
                return v;
            }
        }
        return null;
    }

    public void draws(GraphicsContext gc)
    {

        for(int count = 0; count < points.size(); count++)
        {
            float SIZE = points.get(count).getSIZE();
            points.get(count).draw(gc);
            gc.setStroke(Color.BLACK);
            if((count+1) == points.size())
            {
                gc.strokeLine(points.get(count).getAxisX()+SIZE/2, points.get(count).getAxisY()+SIZE/2, points.get(0).getAxisX()+SIZE/2, points.get(0).getAxisY()+SIZE/2);
            }
            else
            {
                gc.strokeLine(points.get(count).getAxisX()+SIZE/2, points.get(count).getAxisY()+SIZE/2, points.get(count + 1).getAxisX()+SIZE/2, points.get(count + 1).getAxisY()+SIZE/2);
            }
        }
    }
    public void setVertexColor(Color colour)
    {
        for (Vertex v:points)
        {
            v.updateColor(colour);
        }
    }
    public Vertex findSelected()
    {
        for (Vertex v:points)
        {
            if(v.getSelected() == true)
            {
                return v;
            }
        }
        return null;
    }
}
