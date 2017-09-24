package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Vertex
{
    private final double COLLISION_SIZE = 10;
    private final double SIZE = 10;

    private double x = 0, y = 0;
    private boolean selected = false;

    private Color colour = Color.BLUE, color_active = Color.RED;
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

    /**
     * Set the x-axis to the given value.
     * @param new_x The new x-axis.
     */
    void setAxisX (double new_x)
    {
        x = new_x;
    }

    /**
     * Set the y-axis to the given value.
     * @param new_y The new y-axis.
     */
    void setAxisY (double new_y) { y = new_y; }

    /**
     * Set the colour to the given colour.
     * @param new_Color The new colour.
     */
    void setColor (Color new_Color) { colour = new_Color; }

    /**
     * Set the selected state to the given state.
     * @param new_selected The new selected state.
     */
    void setSelected (boolean new_selected) { selected = new_selected; }

    void setNextVertex (Vertex new_nextVertex)
    {
        nextVertex = new_nextVertex;
    }

    /**
     * Getter for the x-axis.
     * @return The x-axis.
     */
    double getAxisX ()
    {
        return x;
    }

    /**
     * Getter for the y-axis.
     * @return The y-axis.
     */
    double getAxisY () { return y; }

    /**
     * Getter for the vertex size.
     * @return The size of the vertex.
     */
    double getSIZE () { return SIZE; }

    /**
     * Getter for the selected state.
     * @return The selected state.
     */
    boolean getSelected () { return selected; }

    /**
     * Handles the collision logic for the vertex.
     * @param new_x X-axis to collide.
     * @param new_y Y-axis to collide.
     * @return True if there is a collision between the vertex and the given points.
     */
    boolean collision (double new_x, double new_y)
    {
        // TODO: Expand for readability.
        return ((x - COLLISION_SIZE) < new_x && (x + COLLISION_SIZE) > new_x) && ((y - COLLISION_SIZE) < new_y && (y + COLLISION_SIZE) > new_y);
    }

    /**
     * Draws the vertex.
     * @param gc GraphicsContext to draw on.
     */
    void draw (GraphicsContext gc, double offset)
    {
        if (selected)
            gc.setStroke(color_active);
        else
            gc.setStroke(colour);

        gc.strokeOval(x*offset-SIZE/2, y*offset-SIZE/2,SIZE,SIZE);

        if (nextVertex!=null) {
            gc.strokeLine(x, y, nextVertex.getAxisX(), nextVertex.getAxisY());
        }
    }

    public void translateX(double x) {
        this.x += x;
    }
    public void translateY(double y) {
        this.y += y;
    }
}
