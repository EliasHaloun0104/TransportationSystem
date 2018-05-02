package com.github.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TaxiDriver implements Initializable {

    @FXML private Button signOutButton;
    private ButtonFunction buttonFunction;

    @FXML private ComboBox delay;
    @FXML private TextArea description;
    @FXML private CheckBox Available;
    @FXML private CheckBox UnAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonFunction = new ButtonFunction(signOutButton);
        buttonFunction.signOutOption();

        //delay
        delay.getItems().addAll("2min","4min","6min","8min","10min","12min","14min");
    }
    public void handleDescription(){

    }


}
