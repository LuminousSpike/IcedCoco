package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
