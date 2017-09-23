package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class PolygonTool implements Tool{
    private Canvas drawingCanvas;
    private PolyList polygons;

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
            Vertex selectedVertex = polygons.getSelectedVertex();
            selectedVertex.setAxisX(e.getSceneX());
            selectedVertex.setAxisY(e.getSceneY());
        }

        draw();
    }

    @Override
    public void onMousePressed(MouseEvent e) {
        if (e.isSecondaryButtonDown()) {
            if (polygons.getCurrentPolygon() != null)
                if (polygons.getCurrentPolygon().size() < 3)
                    polygons.remove(polygons.getCurrentPolygon());

            polygons.setSelectedVertex(null);
            polygons.setCurrentPolygon(null);
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
        if (e.getCode() == KeyCode.DELETE)
        {
            polygons.remove(polygons.getSelectedVertex());
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

        if (polygons.getCurrentPolygon() == null && collidedPoly == null)
            polygons.setCurrentPolygon(polygons.createPoly(x, y));

        else if (collidedPoly == null)
            polygons.getCurrentPolygon().add(x, y, polygons.getSelectedVertex());
        else
            polygons.setCurrentPolygon(collidedPoly);
    }

    private void vertexPress (double x, double y) {
        polygons.setSelectedVertex(polygons.getCurrentPolygon().find(x, y));

        if (polygons.getSelectedVertex() == null)
            polygons.setSelectedVertex(polygons.getCurrentPolygon().popSelectedPoint());
    }
}
