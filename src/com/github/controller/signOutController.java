package com.github.controller;

//import com.github.model.DBConnection;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class signOutController implements Initializable{

    @FXML
    private Label messageLabel;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        yesButton.setOnAction(event -> Platform.exit());

    }




}
