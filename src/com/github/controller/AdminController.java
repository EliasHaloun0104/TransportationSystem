package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class AdminController {

    @FXML private Button signoutButton;
    private ButtonFunction buttonFunction;
    @FXML private VBox textFieldsWrapper;


    public void initialize() {
        buttonFunction = new ButtonFunction(signoutButton);
        buttonFunction.signOutOption();
    }

    @FXML
    private void handlePrintButtonPressed() throws IOException {
        Account.getInstance().printToPdf();
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


