package com.github.controller;

import com.github.model.Destinations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class UserGUI_Controller implements Initializable {
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* fromCombo.getItems().addAll(Destinations.getInstance().getDestinations());
        toCombo.getItems().addAll(Destinations.getInstance().getDestinations());

        fromCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            toCombo.getItems().removeAll(toCombo.getItems());
            toCombo.getItems().addAll(Destinations.getInstance().getDestinationExcept(newString.toString()));
        });

        toCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            try {
               cost.setText(Destinations.getInstance().getSomeThing(fromCombo.getValue().toString(), toCombo.getValue().toString()));
            }catch (NullPointerException e){
                cost.setText(" ");
            }


        });*/

        deposit.textProperty().addListener((observableValue, oldString, newString)->
        {
            handleDepositAmount(oldString, newString);
        });



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
