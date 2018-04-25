package com.github.controller;


import com.github.model.DBConnection;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class AdminController {

    @FXML private Tab complainsTab;
    @FXML private Button signoutButton;
    private ButtonFunction buttonFunction;
    @FXML private VBox textFieldsWrapper;
    @FXML private TextField tfNumberOfComplains, tfDateOfComplain;
    @FXML private ChoiceBox userNameChoiceBox;
    @FXML private TextArea complainMessagetextArea, answerTextArea;


    public void initialize() {
        buttonFunction = new ButtonFunction(signoutButton);
        buttonFunction.signOutOption();
        complainsTab.setOnSelectionChanged(
                e-> {if (complainsTab.isSelected()) {
                    DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
                tfNumberOfComplains.setText(String.valueOf(db.getNumberOfComplains()));

                    }
        });

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


