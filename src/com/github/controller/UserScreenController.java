package com.github.controller;

import com.github.model.DBConnection;
import com.github.model.Destinations;
import com.github.model.RouteCalculate;
import com.github.model.ScheduledRoute;
import com.sun.source.doctree.StartElementTree;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserScreenController implements Initializable {
    @FXML
    ComboBox fromCombo;
    @FXML
    ComboBox toCombo;
    @FXML
    Label cost;
    @FXML
    TextField deposit;
    @FXML
    Button processBtn;
    @FXML
    VBox searchResult;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       fromCombo.getItems().addAll(Destinations.getInstance().getStationsName());
        toCombo.getItems().addAll(Destinations.getInstance().getStationsName());

        fromCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            toCombo.getItems().removeAll(toCombo.getItems());
            toCombo.getItems().addAll(dbConnection.getAvailableDestination(newString.toString()));
        });

        toCombo.valueProperty().addListener((observableValue, oldString, newString) -> {

            setSearchResult();
            /* try {
               //cost.setText(Destinations.getInstance().getSomeThing(fromCombo.getValue().toString(), toCombo.getValue().toString()));
            }catch (NullPointerException e){
                cost.setText(" ");
            }*/


        });

        deposit.textProperty().addListener((observableValue, oldString, newString)->
        {
            handleDepositAmount(oldString, newString);
        });



    }

    private void setSearchResult(){
        try {
            String fromSelect = fromCombo.getSelectionModel().getSelectedItem().toString();
            String toSelect = toCombo.getSelectionModel().getSelectedItem().toString();
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            RouteCalculate scheduledRoutes = dbConnection.getRoutesSearched(fromSelect,toSelect);
            scheduledRoutes.getResult(fromSelect,toSelect);

            searchResult.getChildren().add(scheduledRoutes.getText());

            
        }catch (NullPointerException e){

        }
    }

    private void handleDepositAmount(String oldString , String newString ){
        int length = newString.length();
        int number = 0;
        if(length>0 && length<5){
            try{
                number = Integer.parseInt(newString);
                deposit.setText(String.valueOf(number));
            } catch (NumberFormatException ex){
                deposit.setText(oldString);
            }
        }else {
            deposit.setText(oldString);
            //Alert
        }
        if(number>0 && number<=999999){
            processBtn.setDisable(false);
        }

    }



}
