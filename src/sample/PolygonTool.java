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
    private Vertex selectedVertex;

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
        if (e.isPrimaryButtonDown()) {
            selectedVertex.setAxisX(e.getSceneX());
            selectedVertex.setAxisY(e.getSceneY());
        }

        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            if (currentPolygon != null)
                if (currentPolygon.size() < 3)
                    polygons.remove(currentPolygon);

            currentPolygon = null;
            selectedVertex = null;
            polygons.setSelectedVertex(null);
            return;
        }

        polygonPress(e.getSceneX(), e.getSceneY());
        vertexPress(e.getSceneX(), e.getSceneY());

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

    private void polygonPress (double x, double y) {
        Polygon collidedPoly = polygons.checkCollision(x, y);

        if (currentPolygon == null && collidedPoly == null)
            currentPolygon = polygons.createPoly(x, y);
        else if (collidedPoly == null)
            currentPolygon.add(x, y, selectedVertex);
        else
            currentPolygon = collidedPoly;
    }

    private void vertexPress (double x, double y) {
        selectedVertex = currentPolygon.find(x, y);

        if (selectedVertex == null)
            selectedVertex = currentPolygon.popPoint();

        polygons.setSelectedVertex(selectedVertex);
    }
}
