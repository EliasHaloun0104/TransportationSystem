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
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (taxiId.getText().trim().isEmpty() ) {
            alert.setContentText("The field is empty.\n" +
                    "Please make sure you fill the fields.");
            alert.showAndWait();
        }else
        if (available.isSelected()) {
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(taxiId.getText()), "available");
        }if (unAvailable.isSelected()){
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(taxiId.getText()), "unAvailable");
        }
    }
}
