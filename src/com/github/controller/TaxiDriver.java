package com.github.controller;

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
    @FXML private JFXTreeTableView<TaxiStation> treeTableView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        viewStation();
    }

    @FXML
    private void saveTaxiAvailability(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (stationId.getText().trim().isEmpty() ) {
            alert.setContentText("The field is empty.\n" +
                    "Please make sure you fill the fields.");
            alert.showAndWait();
        }else
        if (available.isSelected()) {
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(stationId.getText()), "available");
        }if (unAvailable.isSelected()){
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
            db.updateAvailability(Integer.parseInt(stationId.getText()), "unAvailable");
        }
    }
    private void viewStation(){
        load("SELECT Station.StationId, Station.Name From Station LEFT JOIN Taxi ON Station.StationId= Taxi.Station_Id");
    }
    private void load(String sql){
        JFXTreeTableColumn<TaxiStation,String> StationId=new JFXTreeTableColumn<>("Id");
        StationId.setPrefWidth(100);
        StationId.setCellValueFactory(e->e.getValue().getValue().stationId);

        JFXTreeTableColumn<TaxiStation,String> StationName=new JFXTreeTableColumn<>("StationName");
        StationName.setPrefWidth(140);
        StationName.setCellValueFactory(e->e.getValue().getValue().stationName);


        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        final TreeItem<TaxiStation> root = new RecursiveTreeItem<>(db.taxiSchedule(sql), RecursiveTreeObject::getChildren);

        treeTableView.getColumns().setAll(StationId,StationName);
        treeTableView.setRoot(root);
        treeTableView.setShowRoot(false);
    }
}
