package com.github.controller;

import javafx.animation.RotateTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;

import java.util.Optional;

public class ButtonFunction {
    private Button button;

    public ButtonFunction(Button button) {
        this.button = button;
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), button);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        button.setOnMouseEntered(e -> rotation.play());

    }
    public void signOutOption(){
        button.setOnAction(event -> signOutButtonPressed());


    }
    public void exitButtonOption(){
        button.setOnAction(event -> StageManager.getInstance().getLogin().hide());
    }
    private void signOutButtonPressed(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out!");
        alert.setHeaderText("Do you wish to sign out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            StageManager.getInstance().showLogin();
        }
    }

}
