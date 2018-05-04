package com.github.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class DeveloperMenu implements Initializable {
    @FXML Button signOutButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.EXIT_PLATFORM);
    }

    @FXML private void simulation() {
        StageManager.getInstance().getSimulation().show();
    }

    @FXML private void userGUI(){
        StageManager.getInstance().getUserScreen().show();
    }
    @FXML private void busDriver(){
        StageManager.getInstance().getDriverScreen().show();
    }
    @FXML private void login(){
        StageManager.getInstance().getLogin().show();
    }
    @FXML private void splashScreen(){
        StageManager.getInstance().getSplashScreen().show();
    }
    @FXML private void taxiDriver(){
        StageManager.getInstance().getTaxiScreen().show();
    }
    @FXML private void adminLoginScreen(){
        StageManager.getInstance().getAdminScreen().show();
    }



}
