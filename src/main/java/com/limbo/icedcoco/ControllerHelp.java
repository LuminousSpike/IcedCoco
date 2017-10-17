package com.limbo.icedcoco;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerHelp implements Initializable{

    private ResourceBundle rb;
    // circles correspond to same numbered pane
    @FXML private Pane helpPane1;
    @FXML private Pane helpPane2;
    @FXML private Pane helpPane3;
    @FXML private Pane helpPane4;
    @FXML private Circle circle1;
    @FXML private Circle circle2;
    @FXML private Circle circle3;
    @FXML private Circle circle4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        //pane.setContent(pane);
        //
        }
}
