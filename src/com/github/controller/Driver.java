package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        viewUpdateTimeTab();
    }
    @FXML
    private void saveDelayAndMessage(){

    }
    private void viewUpdateTimeTab(){
        load("SELECT Station_From,Station_To,StartTime,Delay,DelayMessage FROM Schedule");
    }
    private void load(String sql){
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

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Delays> root = new RecursiveTreeItem<>(db.getSchedule(sql), RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(Station_From,Station_To,StartTime,Delay,DelayMessage);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
    public void handleDescription(){

    }

}