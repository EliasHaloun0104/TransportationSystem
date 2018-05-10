package com.github.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TaxiDriver implements Initializable {

    @FXML private Button signOutButton;
    private ExtendedButton extendedButton;

    @FXML private ComboBox delay;
    @FXML private TextArea Message;
    @FXML private CheckBox Available;
    @FXML private CheckBox UnAvailable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);

    }
    public void handleDescription(){

    }


}
