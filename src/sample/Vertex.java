package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Vertex
{
    private float x = 0;
    private Color colour = Color.BLUE;
    private float y = 0;
    private float COLLISIONSIZE = 10;
    private float SIZE = 10;
    private boolean selected = false;
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
    public void updateAxisY(float new_y) { y = new_y; }
    public void updateColor(Color new_Color) { colour = new_Color; }
    public void updateSelected(boolean new_selected) { selected = new_selected; }
    public float getAxisX()
    {
        return x;
    }
    public float getAxisY() { return y; }
    public float getSIZE() { return SIZE; }
    public boolean getSelected() { return selected; }

    public void setNextVertex(Vertex new_nextVertex)
    {
        nextVertex = new_nextVertex;
    }

    public boolean collision(float new_x, float new_y)
    {
        if(((x - COLLISIONSIZE) < new_x   && (x + COLLISIONSIZE) > new_x)&&((y - COLLISIONSIZE) < new_y   && (y + COLLISIONSIZE) > new_y))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void draw(GraphicsContext gc)
    {
        gc.setStroke(colour);
        gc.strokeOval(x, y,SIZE,SIZE);
        if(nextVertex!=null)
        {
            gc.strokeLine(x, y, nextVertex.getAxisX(), nextVertex.getAxisY());
        }

        //drawing is done here
    }



}
