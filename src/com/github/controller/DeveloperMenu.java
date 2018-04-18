package com.github.controller;

import javafx.fxml.FXML;

public class DeveloperMenu {


    @FXML private void simulation() {
        StageManager.getInstance().switchStage(StageManager.getInstance().getStage2(), StageManager.getInstance().getStage1());
    }

    @FXML private void userGUI(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getUserGUI(), StageManager.getInstance().getStage1());
    }
    @FXML private void busDriver(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getBusScrn(), StageManager.getInstance().getStage1());
    }
    @FXML private void employeeMenu(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getEmployeeMenu(), StageManager.getInstance().getStage1());
    }
    @FXML private void login(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getLogin(), StageManager.getInstance().getStage1());
    }
    @FXML private void splashScreen(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getSplashScreen(), StageManager.getInstance().getStage1());
    }
    @FXML private void taxiDriver(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getTaxiScrn(), StageManager.getInstance().getStage1());
    }
    @FXML private void trainDriver(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getTrainScrn(), StageManager.getInstance().getStage1());
    }@FXML private void adminLoginScreen(){
        StageManager.getInstance().switchStage(StageManager.getInstance().getAdminScrn(), StageManager.getInstance().getStage1());
    }






}
