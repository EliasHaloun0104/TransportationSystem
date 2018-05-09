package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import com.github.model.Destinations;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserScreenController implements Initializable {
    @FXML private Button signOutButton;
    @FXML ComboBox fromCombo;
    @FXML ComboBox toCombo;
    @FXML Label cost;
    @FXML TextField deposit;
    @FXML Button processBtn;
    @FXML ScrollPane searchResults;
    @FXML Button searchButton;
    @FXML Label balance;
    @FXML ScrollPane resultsContainer;
    @FXML Tab balanceTab;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
        fromCombo.getItems().addAll(Destinations.getInstance().getStationsName());
        balance.setText(Account.getInstance().getBalance() + " GD");

        fromCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            toCombo.getItems().removeAll(toCombo.getItems());
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            toCombo.getItems().addAll(dbConnection.getAvailableDestination(Destinations.getInstance().getStationID(newString.toString())));
            searchButton.setDisable(true);
        });

        toCombo.valueProperty().addListener((observableValue, oldString, newString) -> {
            if(fromCombo.getSelectionModel().isEmpty() && toCombo.getSelectionModel().isEmpty()){
                searchButton.setDisable(true);
            }else{
                searchButton.setDisable(false);
            }
        });

        balanceTab.setOnSelectionChanged(event -> setBalance());

        deposit.textProperty().addListener((observableValue, oldString, newString)->
        {
            handleDepositAmount(oldString, newString);
        });




    }

    @FXML private void processButton(){
        Account.getInstance().addToBalance(Integer.valueOf(deposit.getText()));
        balance.setText(Account.getInstance().getBalance() + " GD");

    }

    @FXML private void transactionHistory(){
        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        resultsContainer.setContent(dbConnection.getTransaction());
    }
    @FXML private void bookingHistory(){
        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        resultsContainer.setContent(dbConnection.getBookingHistory());
    }

    @FXML
    private void setSearchResult(){
        try {
            int fromSelect = Destinations.getInstance().getStationID(fromCombo.getSelectionModel().getSelectedItem().toString());
            int toSelect = Destinations.getInstance().getStationID(toCombo.getSelectionModel().getSelectedItem().toString());
            searchResults.setContent(Destinations.getInstance().getScheduledRoutes().getText(fromSelect,toSelect));
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

    public void setBalance() {
        balance.setText(Account.getInstance().getBalance() + " GD");
    }
}
