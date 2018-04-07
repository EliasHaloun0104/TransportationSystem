package com.github.controller;

//import com.github.model.DBConnection;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable{

    @FXML private Button signoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // exit app button animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), signoutButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        signoutButton.setOnMouseEntered(e -> rotation.play());
    }

    @FXML
    private void handleExitAppButton() {
        StageManager.getInstance().setSignOutWindow();
    }
    @FXML
    private void viewProfilePressed(){

    }
    @FXML
    private void editProfilePressed(){

    }
    @FXML
    private void viewBookingHistoryPressed(){

    }


}
