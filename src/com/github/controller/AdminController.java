package com.github.controller;

//import com.github.model.DBConnection;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable{

    @FXML private Button signoutButton,editButton,saveButton;
    @FXML private TextField userNameTextField,firstNameTextField,lastNameTextField,
            emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
            createdDateTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // exit app button animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), signoutButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        signoutButton.setOnMouseEntered(e -> rotation.play());

        ViewProfile viewProfile = new ViewProfile();
        editButton.setOnAction(event -> viewProfile.editButtonPressed(userNameTextField,firstNameTextField,lastNameTextField,
                phoneNbrTextField,newPasswordTextField,confirmPasswordTextField
        ));
        saveButton.setOnAction(event -> viewProfile.saveButtonPressed(userNameTextField,firstNameTextField,lastNameTextField,
                phoneNbrTextField,newPasswordTextField,confirmPasswordTextField
                ));
        signoutButton.setOnAction(event -> viewProfile.signOutButtonPressed());


    }




    }


