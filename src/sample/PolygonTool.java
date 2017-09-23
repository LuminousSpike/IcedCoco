package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PolygonTool implements Tool{
    private Canvas drawingCanvas;
    private PolyList polygons;
    private Polygon currentPolygon;

    public double scale = 1;

    public PolygonTool(PolyList polyList) {
        polygons = polyList;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        drawingCanvas = canvas;
    }

    @Override
    public void onDragEntered(MouseEvent e) {
        draw();
    }

    @Override
    public void onMouseClicked(MouseEvent e) {

        draw();
    }

    @Override
    public void onMouseDragged(MouseEvent e) {
        Vertex v = currentPolygon.popPoint();
        v.setAxisX(e.getSceneX());
        v.setAxisY(e.getSceneY());
        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (currentPolygon == null)
            currentPolygon = polygons.createPoly(e.getSceneX(), e.getSceneY());
        else
            currentPolygon.add(e.getSceneX(), e.getSceneY());

        draw();
    }

    @Override
    public void onMouseReleased(MouseEvent e) {
        draw();
    }

    public void onKeyPress(KeyEvent e)
    {
        //System.out.println("yes");
        if(e.getCode() == KeyCode.DELETE)
        {
            // Delete
        }
        draw();
    }

    /* I'm not too sure about this as of yet.
     * It really depends on how we want to separate the tool modes.
     * Do we display them when you change to the tool?
     * Or is it displayed persistently?
     * */
    @Override
    public void draw () {
        GraphicsContext gc = drawingCanvas.getGraphicsContext2D();
        polygons.draw(gc);
    }
}
