package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import com.github.model.Destinations;
import com.github.model.TimeProcess;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Driver implements Initializable{
    @FXML private Button signOutButton;
    @FXML private ComboBox comboLate;
    @FXML private Button confirmBtn;
    @FXML private Label driverStatus;
    @FXML private TextField delayMessage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        comboLate.getItems().addAll("02 min","04 min","06 min","08 min","10 min");
        comboLate.valueProperty().addListener((observableValue, oldString, newString) -> {
            if(!comboLate.getSelectionModel().isEmpty()){
                confirmBtn.setDisable(false);
            }else{
                confirmBtn.setDisable(true);
            }
        });
        setDriverSchedule();



    }
    @FXML
    private void saveDelayAndMessage() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        String delayString = "00:" + comboLate.getSelectionModel().getSelectedItem().toString().substring(0,2) + ":00";
        db.updateDelayAndMessage(delayString,delayMessage.getText());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmed", ButtonType.OK);
        alert.showAndWait();
        setDriverSchedule();


    }

    private void setDriverSchedule(){
        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        driverStatus.setText(dbConnection.getCurrentRouteForDriver());
    }


}