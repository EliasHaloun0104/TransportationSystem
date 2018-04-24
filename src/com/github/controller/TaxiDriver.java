package com.github.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class TaxiDriver implements Initializable {

    @FXML private Button signoutButton;
    private ButtonFunction buttonFunction;

    @FXML private ComboBox delay;
    @FXML private TextArea description;
    @FXML private Tab viewprofile;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//
//       if (viewprofile.isSelected()){
//        viewProfile.fetchValues(userNameTextField,firstNameTextField,lastNameTextField,emailTextField,phoneNbrTextField);
//        }

        buttonFunction = new ButtonFunction(signoutButton);
        buttonFunction.signOutOption();

        //delay
        delay.getItems().addAll("2min","4min","6min","8min","10min","12min","14min");
    }
    public void handleDescription(){

    }

}
