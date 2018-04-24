package com.github.controller;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BusDriver implements Initializable {
    @FXML
    private Button signoutButton;
    private ButtonFunction buttonFunction;

    @FXML private ComboBox delay;
    @FXML private TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonFunction = new ButtonFunction(signoutButton);
        buttonFunction.signOutOption();

        //delay
        delay.getItems().addAll("2min","4min","6min","8min","10min","12min","14min");
    }
    public void handleDescription(){

    }




}