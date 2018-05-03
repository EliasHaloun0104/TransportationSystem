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
        StageManager.getInstance().switchStage(StageManager.getInstance().getSimulation(), StageManager.getInstance().getDeveloperMenu());
    }

    @FXML private void userGUI(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getUserScreen(), StageManager.getInstance().getDeveloperMenu());
    }
    @FXML private void busDriver(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getDriverScreen(), StageManager.getInstance().getDeveloperMenu());
    }
    @FXML private void login(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getLogin(), StageManager.getInstance().getDeveloperMenu());
    }
    @FXML private void splashScreen(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getSplashScreen(), StageManager.getInstance().getDeveloperMenu());
    }
    @FXML private void taxiDriver(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getTaxiScreen(), StageManager.getInstance().getDeveloperMenu());
    }
    @FXML private void adminLoginScreen(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getAdminScreen(), StageManager.getInstance().getDeveloperMenu());
    }



}
