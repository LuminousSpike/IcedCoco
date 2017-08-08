package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

public class Main extends Application {
    @FXML
    Pane canvasPane;

    private Canvas canvas;
    private List<Polygon> polygons = new LinkedList<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_main.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        primaryStage.setTitle("Comp314 Project");
        Scene scene = new Scene (root, 640, 480);

        // add listeners to the scene
        scene.widthProperty().addListener((observableValue, oldWidth, newWidth) -> cont.onWindowResize());
        scene.heightProperty().addListener((observableValue, oldHeight, newHeight) -> cont.onWindowResize());

        primaryStage.setScene(scene);
        cont.setScene(scene);
        cont.setStage(primaryStage);
        primaryStage.show();

        Polygon p = new Polygon();
        p.add(20,20);
        p.add(100,100);
        p.add(40,20);
        p.add(40,50);
        polygons.add(p);
        Polygon p2 = new Polygon();
        polygons.add(p2);
        canvas = (Canvas) root.lookup("#canvas");
        draw(canvas);







        Button tool1 = (Button) root.lookup("#tool1");
        Button tool2 = (Button) root.lookup("#tool2");
        tool1.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event)
            {
                canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Action " + (event.getX())+ " - " + event.getY());
                        Vertex selectedVertex = null;
                        for (Polygon p: polygons) {
                            if ((selectedVertex = p.findSelected()) != null) {
                                if(polygons.get(polygons.size()-1).size() > 2) {
                                    polygons.add(new Polygon());
                                }
                                System.out.println("Stop");
                                break;
                            }
                        }
                        if(selectedVertex == null) {

                            polygons.get(polygons.size()-1).add((float) event.getX(), (float) event.getY());
                            draw(canvas);
                        }
                    }
                });
                canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Drag " + (event.getX())+ " - " + event.getY());
                        Vertex selectedVertex  = null;
                        for (Polygon p: polygons)
                        {
                            if((selectedVertex = p.findSelected()) != null)
                            {
                                System.out.println("Vertex updated");
                                selectedVertex.updateAxisX((float)event.getX());
                                selectedVertex.updateAxisY((float)event.getY());
                                draw(canvas);
                            }
                        }
                    }
                });

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Pressed " + (event.getX())+ " - " + event.getY());
                        Vertex selectedVertex  = null;
                        for (Polygon p: polygons)
                        {
                            if((selectedVertex = p.findSelected())!= null)
                            {
                                selectedVertex.updateSelected(false);
                            }
                            p.setVertexColor(Color.BLUE);
                            if((selectedVertex = p.find((float) event.getX(), (float) event.getY())) != null)
                            {
                                selectedVertex.updateColor(Color.RED);
                                selectedVertex.updateSelected(true);
                                System.out.println("Vertex pressed ");
                                draw(canvas);
                            }

                        }
                    }
                });

                canvas.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        Vertex v = null;
                        for (Polygon p: polygons)
                        {
                            p.setVertexColor(Color.BLUE);
                            if((v = p.find((float) event.getX(), (float) event.getY())) != null)
                            {
                                v.updateColor(Color.RED);
                            }
                        }
                        if(v != null)
                        {
                            v.updateAxisX((float)event.getX());
                            v.updateAxisY((float)event.getY());
                        }
                    }
                });
            }

        });
        tool2.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event)
            {
                canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });
                canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });

                canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                    }
                });

                canvas.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {

                    }
                });
            }

        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void draw(Canvas c)
    {
        GraphicsContext gc = c.getGraphicsContext2D();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Polygon p: polygons)
        {
            p.draws(gc);
        }
    }
}
