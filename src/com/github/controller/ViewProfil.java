package com.github.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ViewProfil {

    @FXML private TextField  UserName,FristName,LastName,EmailAddress;

    @FXML private void BackButtonPressed(ActionEvent event){
        StageManager.getInstance().setUserLoggedscrn();
    }
}
