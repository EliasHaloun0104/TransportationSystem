package com.github.controller;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;

public class TrainDriver implements Initializable {

    @FXML private Button signoutButton,editButton,saveButton;
    @FXML private TextField userNameTextField,firstNameTextField,lastNameTextField,
            emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
            createdDateTextField;
    ArrayList<TextField> textFields;
    ArrayList<Button> buttons;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = new ArrayList<>(Arrays.asList(userNameTextField,firstNameTextField,lastNameTextField,emailTextField,phoneNbrTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField));
        buttons = new ArrayList<>(Arrays.asList(signoutButton,editButton,saveButton));

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
        for (TextField t: textFields) {
            t.setEditable(true);
            t.setDisable(false);
        }

    }
    @FXML
    private void saveButtonPressed(){
        for (TextField t: textFields) {
            t.setEditable(false);
            t.setDisable(true);
        }

    }

}
