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


    }

    @FXML
    private void handleExitAppButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out!");
        alert.setHeaderText("Do you wish to sign out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            StageManager.getInstance().showLogin();
        }
    }
    @FXML
    private void editButtonPressed(){

        firstNameTextField.setEditable(true);
        lastNameTextField.setEditable(true);
        phoneNbrTextField.setEditable(true);
        roleTextField.setEditable(true);
        newPasswordTextField.setEditable(true);
        confirmPasswordTextField.setEditable(true);

        firstNameTextField.setDisable(false);
        lastNameTextField.setDisable(false);
        phoneNbrTextField.setDisable(false);
        roleTextField.setDisable(false);
        newPasswordTextField.setDisable(false);
        confirmPasswordTextField.setDisable(false);

    }
    @FXML
    private void saveButtonPressed(){
        firstNameTextField.setEditable(false);
        lastNameTextField.setEditable(false);
        phoneNbrTextField.setEditable(false);
        roleTextField.setEditable(false);
        newPasswordTextField.setEditable(false);
        confirmPasswordTextField.setEditable(false);

        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
        phoneNbrTextField.setDisable(true);
        roleTextField.setDisable(true);
        newPasswordTextField.setDisable(true);
        confirmPasswordTextField.setDisable(true);

    }



}
