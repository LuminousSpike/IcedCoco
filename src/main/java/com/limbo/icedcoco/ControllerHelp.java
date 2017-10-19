package com.limbo.icedcoco;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.net.URL;

public class ControllerHelp implements Initializable{

    private ResourceBundle rb;
    private Scene scene;

    @FXML private Pane topbar;
    @FXML private Pane pane1;
    @FXML private Pane pane2;
    @FXML private Pane pane3;
    @FXML private Pane pane4;
    @FXML private Pane pane5;
    @FXML private Pane pane6;
    @FXML private Circle circle1;
    @FXML private Circle circle2;
    @FXML private Circle circle3;
    @FXML private Circle circle4;
    @FXML private Circle circle5;
    @FXML private Circle circle6;
    ArrayList<Circle> circleList = new ArrayList<>();

    public void setScene(Scene s){
        this.scene = s;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.rb = resources;
        circleList.add(circle1);
        circleList.add(circle2);
        circleList.add(circle3);
        circleList.add(circle4);
        circleList.add(circle5);
        circleList.add(circle6);

        //pane.setContent(pane);
    }
    /*
    @FXML
    private Shape circle1, circle2, circle3, circle4;
    @FXML
    private AnchorPane pane1, pane2, pane3;
    */




    @FXML
    private void handleClickCircle (MouseEvent event) {
        Circle target = (Circle)event.getTarget();
        pane1.setVisible(target.equals(circle1));
        pane2.setVisible(target.equals(circle2));
        pane3.setVisible(target.equals(circle3));
        pane4.setVisible(target.equals(circle4));
        pane5.setVisible(target.equals(circle5));
        pane6.setVisible(target.equals(circle6));

        for (Integer i = 1; i <= 6; i++)
        {
            Circle current = circleList.get(i-1);
            if (current == target) {
                current.setOpacity(0.5);
            }
            else
            {
                current.setOpacity(1.0);
            }
        }
    }

    @FXML
    public void menuExit(Event event) {
        ((Stage)scene.getWindow()).close();     // don't let scene be null
    }
}
