package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Driver implements Initializable {
    @FXML private Button signOutButton;


    @FXML private TextField delay;
    @FXML private TextArea DriverMessage;

    @FXML
    private JFXTreeTableView<Delays> treeView;

          private ArrayList<Delays> delays = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);

    }
    @FXML
    private void viewUpdateTimeTabSelected(){

        load("SELECT Station_From,Station_To,StartTime,Delay,DelayMessage, FROM Schedule");
    }
    private void load(String sql){


        JFXTreeTableColumn<Delays,String> Station_From=new JFXTreeTableColumn<>("From");
        Station_From.setPrefWidth(30);
        Station_From.setCellValueFactory(e->e.getValue().getValue().From);

        JFXTreeTableColumn<Delays,String> Station_To=new JFXTreeTableColumn<>("To");
        Station_To.setPrefWidth(50);
        Station_To.setCellValueFactory(e->e.getValue().getValue().To);

        JFXTreeTableColumn<Delays,String> StartTime=new JFXTreeTableColumn<>("StartTime");
        StartTime.setPrefWidth(50);
        StartTime.setCellValueFactory(e->e.getValue().getValue().ActualTime);

        JFXTreeTableColumn<Delays,String> Delay=new JFXTreeTableColumn<>("Delay");
        Delay.setPrefWidth(50);
        Delay.setCellValueFactory(e->e.getValue().getValue().Delay);

        JFXTreeTableColumn<Delays,String> DelayMessage=new JFXTreeTableColumn<>("DelayMessage");
        DelayMessage.setPrefWidth(50);
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