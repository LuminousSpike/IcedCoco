package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Vertex
{
    private final double COLLISION_SIZE = 10;
    private final double SIZE = 10;

    private double x = 0, y = 0;
    private boolean selected = false;

    private Color colour = Color.BLUE;
    private Vertex nextVertex = null;

    Vertex (double new_x, double new_y)
    {
        x = new_x;
        y = new_y;
    }

    public Vertex (double new_x, double new_y, Vertex new_nextVertex)
    {
        x = new_x;
        y = new_y;
        nextVertex = new_nextVertex;
    }

    void updateAxisX (double new_x)
    {
        x = new_x;
    }
    void updateAxisY (double new_y) { y = new_y; }
    void updateColor (Color new_Color) { colour = new_Color; }
    void updateSelected (boolean new_selected) { selected = new_selected; }
    double getAxisX ()
    {
        return x;
    }
    double getAxisY () { return y; }
    double getSIZE () { return SIZE; }
    boolean getSelected () { return selected; }

    void setNextVertex (Vertex new_nextVertex)
    {
        nextVertex = new_nextVertex;
    }

    boolean collision (double new_x, double new_y)
    {
        // TODO: Expand for readability.
        return ((x - COLLISION_SIZE) < new_x && (x + COLLISION_SIZE) > new_x) && ((y - COLLISION_SIZE) < new_y && (y + COLLISION_SIZE) > new_y);
    }

    void draw (GraphicsContext gc)
    {
        gc.setStroke(colour);
        gc.strokeOval(x, y,SIZE,SIZE);

        if (nextVertex!=null) {
            gc.strokeLine(x, y, nextVertex.getAxisX(), nextVertex.getAxisY());
        }
    }
}
