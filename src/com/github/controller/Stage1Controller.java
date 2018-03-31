package com.github.controller;

import com.github.model.DBConnection;
import javafx.fxml.FXML;

public class Stage1Controller {

    public void initialize() {
        DBConnection db = new DBConnection();
    }

    @FXML private void simulation() {
        StageManager.getInstance().showStage2();
    }

    @FXML private void taxiInterface() {
        StageManager.getInstance().showTaxiInterface();
    }


}
