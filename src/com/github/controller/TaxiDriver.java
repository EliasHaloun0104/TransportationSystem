package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import com.github.model.Destinations;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TaxiDriver implements Initializable {

    @FXML private Button signOutButton;
    @FXML private CheckBox available;
    @FXML private CheckBox unAvailable;
    @FXML private JFXTextField stationId;
    @FXML private JFXTextField taxiId;
    @FXML private JFXTreeTableView<TaxiStation> treeTableView;
    @FXML private ComboBox comboTaxi;
    @FXML private Button confirmBtn;
    @FXML private Label taxiStatus;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        comboTaxi.getItems().addAll(Destinations.getInstance().getRegionName());
        comboTaxi.valueProperty().addListener((observableValue, oldString, newString) -> {
            if(!comboTaxi.getSelectionModel().isEmpty()){
                confirmBtn.setDisable(false);
            }else{
                confirmBtn.setDisable(true);
            }
        });
        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        taxiStatus.setText(dbConnection.checkTaxiAvailabilities());

    }


    @FXML
    private void saveTaxiAvailability(){
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        int stationId = Destinations.getInstance().getStationID(comboTaxi.getSelectionModel().getSelectedItem().toString());
        db.updateAvailability(stationId, 1);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmed", ButtonType.OK);
        alert.showAndWait();
        taxiStatus.setText("Your Status: Available in " + comboTaxi.getSelectionModel().getSelectedItem().toString());
    }
    @FXML
    private void saveTaxiUnAvailability(){
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateAvailability(1, 0);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Confirmed", ButtonType.OK);
        alert.showAndWait();
        taxiStatus.setText("Your Status: UnAvailable");
    }

}
