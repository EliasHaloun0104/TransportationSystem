package com.github.controller;

import javafx.fxml.FXML;

public class EmployeeMenu {


    @FXML
    private void taxiPressed(){
        StageManager.getInstance().setTaxiDriver();
    }
    @FXML
    private void busPressed(){
        StageManager.getInstance().setBusDriver();
    }
    @FXML
    private void adminPressed(){
        StageManager.getInstance().setAdminScrn();
    }
    @FXML
    private void trainPressed(){
        StageManager.getInstance().setTrainDriver();
    }
}
