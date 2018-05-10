package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TaxiDriver implements Initializable {

    @FXML private Button signOutButton;
    private ExtendedButton extendedButton;
    @FXML private CheckBox available;
    @FXML private CheckBox unAvailable;
    @FXML private JFXTextField taxiId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);

    }

    @FXML
    private void saveTaxiAvailability(){
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

    }
}
