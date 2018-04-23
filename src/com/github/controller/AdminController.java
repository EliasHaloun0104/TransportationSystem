package com.github.controller;

import com.github.model.Account;
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
            }
        }
        saveButton.setDisable(false);
    }

    @FXML
    private void handleSaveButtonPressed() {

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


