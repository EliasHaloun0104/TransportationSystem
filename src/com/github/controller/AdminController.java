package com.github.controller;


import com.github.model.ComplainPerson;
import com.github.model.DBConnection;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
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
    private ArrayList<ComplainPerson> complainPeople = new ArrayList<>();



    public void initialize() {
        buttonFunction = new ButtonFunction(signoutButton);
        buttonFunction.signOutOption();

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

    @FXML
    private void complainsTabSelected(Event event) {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        tfNumberOfComplains.setText(String.valueOf(db.getNumberOfComplains()));


        setComplains(complainPeople);


        Platform.runLater(() -> {


            userNameChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {

                tfDateOfComplain.setText(complainPeople.get(newValue.intValue()).getDate());
                complainMessagetextArea.setText(complainPeople.get(newValue.intValue()).getMessage());
            });

            loadComplains();

            if (!complainPeople.isEmpty()) {
                addComplainsToChoice();
                userNameChoiceBox.getSelectionModel().select(0);
            }

        });

    }

    private void addComplainsToChoice() {

        userNameChoiceBox.getItems().clear();
        for (ComplainPerson person : complainPeople) {
            userNameChoiceBox.getItems().add(person);
        }
    }

    public void setComplains(ArrayList<ComplainPerson> complains) {
        this.complainPeople = complains;
        addComplainsToChoice();
    }

    private void loadComplains() {
        DBConnection db1 = new DBConnection(DBConnection.ConnectionType.ADMIN);
        complainPeople = db1.getComplain();

    }
}


