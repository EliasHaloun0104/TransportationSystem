package com.github.controller;

import javafx.fxml.FXML;

public class Stage1Controller {

    @FXML private void switcher() {
        StageManager.getInstance().showStage_2();
    }
}
