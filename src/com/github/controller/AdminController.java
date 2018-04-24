package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class AdminController {

    @FXML private Button signoutButton, editButton, saveButton;
    @FXML private TextField userNameTextField, firstNameTextField, lastNameTextField,
            emailTextField, phoneNbrTextField, roleTextField, newPasswordTextField, confirmPasswordTextField,
            createdDateTextField;
    @FXML private Tab viewProfileTab;
    @FXML private VBox textFieldsWrapper;

    public void initialize() {
        signoutButtonAnimation();
        viewProfile();

    }

    private void signoutButtonAnimation() {
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), signoutButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        signoutButton.setOnMouseEntered(e -> rotation.play());
    }

    private void viewProfile() {
        viewProfileTab.setOnSelectionChanged(t -> {
            if (viewProfileTab.isSelected()) {
                userNameTextField.setText(Account.getInstance().getAccountId());
                firstNameTextField.setText(Account.getInstance().getFirstName());
                lastNameTextField.setText(Account.getInstance().getLastName());
                emailTextField.setText(Account.getInstance().getEmail());
            }
        });
    }

    @FXML
    private void handleEditButtonPressed() {
        List<Node> allNodes = textFieldsWrapper.getChildren();
        for (Node n : allNodes) {
            if (n instanceof TextField) {
                n.setDisable(false);
                ((TextField) n).setEditable(true);
                userNameTextField.setDisable(true);
                roleTextField.setDisable(true);
                emailTextField.setDisable(true);
                createdDateTextField.setDisable(true);
            }
        }
        saveButton.setDisable(false);
    }

    @FXML
    private void handleSaveButtonPressed() {
        boolean status = true;
        if (firstNameTextField.getText().trim().isEmpty()||lastNameTextField.getText().trim().isEmpty()||
                phoneNbrTextField.getText().trim().isEmpty()||newPasswordTextField.getText().trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid");
            alert.setHeaderText("Fill the fields");
            alert.setContentText("Please make sure that all the fields are filled with your info");
            alert.showAndWait();
            status = false;
        }
        if (!newPasswordTextField.getText().equals(confirmPasswordTextField.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid");
            alert.setHeaderText("Confirmation password doesn't match");
            alert.setContentText("Please make sure that both the new password and confirmation password match");
            newPasswordTextField.setText("");
            confirmPasswordTextField.setText("");
            alert.showAndWait();
            status =false;
        }if (status){
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
            dbConnection.updateAccountDetails(firstNameTextField.getText(),lastNameTextField.getText(),phoneNbrTextField.getText(),
                    newPasswordTextField.getText());
        }

    }

    @FXML
    private void handleSignoutButtonPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out!");
        alert.setHeaderText("Do you wish to sign out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            StageManager.getInstance().showLogin();
            List<Node> allNodes = textFieldsWrapper.getChildren();
            for (Node n : allNodes) {
                if (n instanceof TextField) {
                    n.setDisable(true);
                    ((TextField) n).setEditable(false);
                }
            }
        }
    }
}


