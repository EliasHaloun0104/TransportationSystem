package com.github.controller;

import com.github.model.DBConnection;
import com.github.model.Destinations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UserScreenController implements Initializable {
    @FXML private Button signOutButton;
    @FXML ComboBox fromCombo;
    @FXML ComboBox toCombo;
    @FXML Label cost;
    @FXML TextField deposit;
    @FXML Button processBtn;
    @FXML VBox searchResult;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        fromCombo.getItems().addAll(Destinations.getInstance().getStationsName());

        fromCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            toCombo.getItems().removeAll(toCombo.getItems());
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            toCombo.getItems().addAll(dbConnection.getAvailableDestination(Destinations.getInstance().getStationID(newString.toString())));
        });

        toCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            try{
                if(!newString.equals(null)){
                    setSearchResult();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }

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
            int fromSelect = Destinations.getInstance().getStationID(fromCombo.getSelectionModel().getSelectedItem().toString());
            int toSelect = Destinations.getInstance().getStationID(toCombo.getSelectionModel().getSelectedItem().toString());
            searchResult.getChildren().add(Destinations.getInstance().getScheduledRoutes().getText(fromSelect,toSelect));
        }catch (NullPointerException e){
            e.printStackTrace();
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
