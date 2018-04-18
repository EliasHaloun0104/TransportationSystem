package com.github.controller;

import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class BusDriver implements Initializable {
    @FXML
    private Button signoutButton, editButton, saveButton;
    @FXML
    private TextField userNameTextField, firstNameTextField, lastNameTextField,
            emailTextField, phoneNbrTextField, roleTextField, newPasswordTextField, confirmPasswordTextField,
            createdDateTextField;
    @FXML private ComboBox delay;
    @FXML private TextArea description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ViewProfile viewProfile = new ViewProfile();
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), signoutButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        signoutButton.setOnMouseEntered(e -> rotation.play());
        editButton.setOnAction(event -> viewProfile.editButtonPressed(userNameTextField, firstNameTextField, lastNameTextField,
                phoneNbrTextField, newPasswordTextField, confirmPasswordTextField
        ));
        saveButton.setOnAction(event -> viewProfile.saveButtonPressed(userNameTextField, firstNameTextField, lastNameTextField,
                phoneNbrTextField, newPasswordTextField, confirmPasswordTextField
        ));
        signoutButton.setOnAction(event -> viewProfile.signOutButtonPressed());
        //delay
        delay.getItems().addAll("2min","4min","6min","8min","10min","12min","14min");
    }
    public void handleDescription(){

    }




}