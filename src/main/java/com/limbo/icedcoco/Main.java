package com.limbo.icedcoco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_main.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        primaryStage.setTitle("Comp314 Project");
        Scene scene = new Scene (root, 1280, 790);

        // add listeners to the scene
        scene.widthProperty().addListener((observableValue, oldWidth, newWidth) -> cont.onWindowResize());
        scene.heightProperty().addListener((observableValue, oldHeight, newHeight) -> cont.onWindowResize());

        primaryStage.setScene(scene);
        cont.setScene(scene);
        cont.setStage(primaryStage);
        cont.start();
        primaryStage.show();

        //HelpUI();
    }

    public void HelpUI() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("form_help.fxml"));
        Parent root = loader.load();
        ControllerHelp cont = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        cont.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
