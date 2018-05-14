package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
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
    private ExtendedButton extendedButton;
    @FXML private CheckBox available;
    @FXML private CheckBox unAvailable;
    @FXML private JFXTextField stationId;
    @FXML private JFXTextField taxiId;
    @FXML private JFXTreeTableView<TaxiStation> treeTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        viewStation();

    }
    @FXML
    private void addDriver(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (stationId.getText().trim().isEmpty() ) {
            alert.setContentText("The field is empty.\n" +
                    "Please make sure you fill the fields.");
            alert.showAndWait();
        }else if  (!stationId.getText().matches("\\d+")){
            alert.setContentText("The Station-Id field should be digits.\n"+"Please make sure you fill the fields.");
            alert.showAndWait();
        }
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.setTaxiDriver(Account.getInstance().getAccountId(),stationId.getText());
    }

    @FXML
    private void saveTaxiAvailability(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (taxiId.getText().trim().isEmpty() ) {
            alert.setContentText("The field is empty.\n" +
                    "Please make sure you fill the fields.");
            alert.showAndWait();
        }else if  (!taxiId.getText().matches("\\d+")){
            alert.setContentText("The Taxi-Id field should be digits.\n"+"Please make sure you fill the fields.");
            alert.showAndWait();
        }
        if (available.isSelected()) {
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(taxiId.getText()), "Available");
        }
        if (unAvailable.isSelected()){
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(taxiId.getText()), "NotAvailable");
        }

    }
    @FXML
    private void availableCheckBox(){
    unAvailable.setSelected(false);
    }
    @FXML
    private void notavailableCheckBox(){
        available.setSelected(false);
    }
    private void viewStation(){
        load("SELECT TaxiId,TaxiStatus,Account_Username,Station_Id,StationId,Name FROM Taxi RIGHT JOIN Station on TaxiId = StationId AND Account_Username ='"+ Account.getInstance().getAccountId()+"'");
    }
    private void load(String sql){
        JFXTreeTableColumn<TaxiStation,String> taxiId=new JFXTreeTableColumn<>("taxiId");
        taxiId.setPrefWidth(80);
        taxiId.setCellValueFactory(e->e.getValue().getValue().taxiId);

        JFXTreeTableColumn<TaxiStation,String> taxiStatus=new JFXTreeTableColumn<>("taxiStatus");
        taxiStatus.setPrefWidth(80);
        taxiStatus.setCellValueFactory(e->e.getValue().getValue().taxiStatus);

        JFXTreeTableColumn<TaxiStation,String> account_userName=new JFXTreeTableColumn<>("account_userName");
        account_userName.setPrefWidth(80);
        account_userName.setCellValueFactory(e->e.getValue().getValue().account_userName);

        JFXTreeTableColumn<TaxiStation,String> station_Id=new JFXTreeTableColumn<>("station_Id");
        station_Id.setPrefWidth(80);
        station_Id.setCellValueFactory(e->e.getValue().getValue().station_Id);

        JFXTreeTableColumn<TaxiStation,String> stationId=new JFXTreeTableColumn<>("stationId");
        stationId.setPrefWidth(80);
        stationId.setCellValueFactory(e->e.getValue().getValue().stationId);

        JFXTreeTableColumn<TaxiStation,String> stationName=new JFXTreeTableColumn<>("StationName");
        stationName.setPrefWidth(80);
        stationName.setCellValueFactory(e->e.getValue().getValue().stationName);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        final TreeItem<TaxiStation> root = new RecursiveTreeItem<>(db.taxiStation(sql), RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(taxiId,taxiStatus,account_userName,station_Id,stationId,stationName);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }
}
