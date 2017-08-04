package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Vertex
{
    private float x = 0;
    private float y = 0;
    private Vertex nextVertex = null;
    public Vertex(float new_x, float new_y)
    {
        x = new_x;
        y = new_y;
    }

    public Vertex(float new_x, float new_y, Vertex new_nextVertex)
    {
        x = new_x;
        y = new_y;
        nextVertex = new_nextVertex;
    }

    public void updateAxisX(float new_x)
    {
        x = new_x;
    }
    public void updateAxisY(float new_y)
    {
        y = new_y;
    }
    public float getAxisX()
    {
        return x;
    }
    public float getAxisY()
    {
        return y;
    }
    public void setNextVertex(Vertex new_nextVertex)
    {
        nextVertex = new_nextVertex;
    }

    public void draw(GraphicsContext gc)
    {
        gc.setStroke(Color.BLUE);
        gc.strokeOval(x, y,10,10);
        if(nextVertex!=null)
        {
            gc.strokeLine(x, y, nextVertex.getAxisX(), nextVertex.getAxisY());
        }
        //drawing is done here
    }



}
