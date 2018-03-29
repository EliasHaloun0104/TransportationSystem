package com.github.controller;

import javafx.fxml.FXML;

public class Stage1Controller {

    @FXML private void simulation() {
        StageManager.getInstance().showStage_2();
    }

    @FXML private void taxiInterface() {
        StageManager.getInstance().showTaxiInterface();
    }

}
