package sample;

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
        primaryStage.setScene(new Scene(root, 640, 480));
        cont.setScene(primaryStage.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
