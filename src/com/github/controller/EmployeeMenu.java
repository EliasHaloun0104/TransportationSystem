package com.github.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class EmployeeMenu {

    @FXML private Button Taxi,Bus,Train;

    @FXML
    public void Driver(){
        if(Taxi.isPressed()){
            StageManager.getInstance().setTaxiDriver();
        }else if (Bus.isPressed()){
            StageManager.getInstance().setBusDriver();
        }else if (Train.isPressed()) {
            StageManager.getInstance().setTrainDriver();
        }
        StageManager.getInstance().setAdminScrn();
    }
}
