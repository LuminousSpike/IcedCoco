package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import javax.swing.*;

public class Controller{

    private SessionInfo sessionInfo = new SessionInfo();

    @FXML
    public void menuOpenImage(ActionEvent event){
        System.out.println("Open file");
    }

    @FXML
    public void menuOpenImageWithCoco(ActionEvent event){
        System.out.println("Open image with metadata");
    }

    @FXML
    public void menuSaveData(ActionEvent event){
        System.out.println("saving to .json files...");
    }

    @FXML
    public void menuExit(ActionEvent event){

    }



}
