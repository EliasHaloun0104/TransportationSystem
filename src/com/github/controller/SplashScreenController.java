package com.github.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable{

    @FXML public static ImageView image;
    @FXML private AnchorPane rootPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000));
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setNode(rootPane);
//        fadeTransition.setOnFinished(event ->
//                StageManager.getInstance().showLogin());
        fadeTransition.play();

    }





}
