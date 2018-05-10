package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Driver implements Initializable {

    @FXML private Button signOutButton;
    @FXML private JFXTextField delay;
    @FXML private JFXTextArea driverMessage;
    @FXML private JFXTreeTableView<Delays> treeView;
    @FXML private JFXTextField taxiId ;
    private DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        viewUpdateTimeTab();

    }
    @FXML
    private void saveDelayAndMessage() {
        Alert alert = new Alert(Alert.AlertType.WARNING);

            if (delay.getText().trim().isEmpty() || driverMessage.getText().trim().isEmpty() || taxiId.getText().trim().isEmpty()) {
                alert.setContentText("The fields are empty.\n" +
                        "Please make sure you fill the fields.");
                alert.showAndWait();
            }
            if ((!delay.getText().matches("\\d+")) || !taxiId.getText().matches("\\d+")) {
                alert.setContentText("The Delay,TaxiId fields should be digits.\n"+"Please make sure you fill the fields.");
                alert.showAndWait();
            }
            db.updateDelayAndMessage(Integer.parseInt(taxiId.getText()),delay.getText(),driverMessage.getText());
    }

    private void viewUpdateTimeTab(){
        load("SELECT ScheduleId,Station_From,Station_To,StartTime,Delay,DelayMessage FROM Schedule");
    }
    private void load(String sql){
        JFXTreeTableColumn<Delays,String> scheduleId=new JFXTreeTableColumn<>("Id");
        scheduleId.setPrefWidth(30);
        scheduleId.setCellValueFactory(e->e.getValue().getValue().ScheduleId);

        JFXTreeTableColumn<Delays,String> Station_From=new JFXTreeTableColumn<>("From");
        Station_From.setPrefWidth(100);
        Station_From.setCellValueFactory(e->e.getValue().getValue().From);

        JFXTreeTableColumn<Delays,String> Station_To=new JFXTreeTableColumn<>("To");
        Station_To.setPrefWidth(100);
        Station_To.setCellValueFactory(e->e.getValue().getValue().To);

        JFXTreeTableColumn<Delays,String> StartTime=new JFXTreeTableColumn<>("StartTime");
        StartTime.setPrefWidth(100);
        StartTime.setCellValueFactory(e->e.getValue().getValue().ActualTime);

        JFXTreeTableColumn<Delays,String> Delay=new JFXTreeTableColumn<>("Delay");
        Delay.setPrefWidth(100);
        Delay.setCellValueFactory(e->e.getValue().getValue().Delay);

        JFXTreeTableColumn<Delays,String> DelayMessage=new JFXTreeTableColumn<>("DelayMessage");
        DelayMessage.setPrefWidth(130);
        DelayMessage.setCellValueFactory(e->e.getValue().getValue().Message);


        final TreeItem<Delays> root = new RecursiveTreeItem<>(db.getSchedule(sql), RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(scheduleId,Station_From,Station_To,StartTime,Delay,DelayMessage);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
    public void handleDescription(){

    }

}