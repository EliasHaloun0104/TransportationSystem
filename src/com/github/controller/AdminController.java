package com.github.controller;


import com.github.model.ComplainPerson;
import com.github.model.DBConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminController {


    @FXML
    private TextField userNameTextField,firstNameTextField,lastNameTextField, emailTextField,phoneNbrTextField,
            roleTextField,passwordTextField,confirmPasswordTextField, vehicleNumberTextField;
    @FXML
    private Tab complainsTab;
    @FXML
    private Button signOutButton, compensateButton, ApologyButton;
    @FXML
    private VBox textFieldsWrapper;
    @FXML
    private TextField tfNumberOfComplains, tfDateOfComplain;
    @FXML
    private ChoiceBox userNameChoiceBox;
    @FXML
    private TextArea complainMessagetextArea, answerTextArea;
    private ArrayList<ComplainPerson> complainPeople = new ArrayList<>();
    @FXML private TableView<String> tableView = new TableView<>();


    public void initialize() {
        ExtendedButton.setFunction(signOutButton,ExtendedButton.Type.TO_LOGIN);
    }


    @FXML
    private void handleSignOutButtonPressed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out!");
        alert.setHeaderText("Do you wish to sign out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            StageManager.getInstance().getLogin();
            //StageManager.getInstance().getAdminScreen().hide();
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
    private void complainsTabSelected() {

        setTfNumberOfComplains();

        setComplains(complainPeople);

        Platform.runLater(() -> {


            userNameChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
                setDateAndMessage();

//                tfDateOfComplain.setText(complainPeople.get(newValue.intValue()).getDate());
//                complainMessagetextArea.setText(complainPeople.get(newValue.intValue()).getMessage());
            });

            loadComplains();
            if (!complainPeople.isEmpty()) {
                addComplainsToChoice();
                userNameChoiceBox.getSelectionModel().select(0);
            }
        });
    }

    @FXML
    private void ApologyButtonPressed() {
        System.out.println(userNameChoiceBox.getValue());

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateComplainAnswer(answerTextArea.getText(), String.valueOf(userNameChoiceBox.getValue()));
        setTfNumberOfComplains();

        complainsTabSelected();

        answerTextArea.setText("");



    }

    @FXML
    private void compensateButtonPressed() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateComplainAnswer(answerTextArea.getText(), String.valueOf(userNameChoiceBox.getValue()));

        DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
        dbConnection.addCompensationValue(String.valueOf(userNameChoiceBox.getValue()));
        setTfNumberOfComplains();
        complainsTabSelected();

        answerTextArea.setText("");

    }
    @FXML
    private void createButtonPressed(){
        boolean status = true;

            if (userNameTextField.getText().trim().isEmpty()||firstNameTextField.getText().trim().isEmpty()||
                    lastNameTextField.getText().trim().isEmpty()|| phoneNbrTextField.getText().trim().isEmpty()||
                    passwordTextField.getText().trim().isEmpty() ||confirmPasswordTextField.getText().trim().isEmpty()||
                    roleTextField.getText().trim().isEmpty()||emailTextField.getText().trim().isEmpty()||
                    vehicleNumberTextField.getText().trim().isEmpty()){
                Alert a = new Alert(Alert.AlertType.WARNING, "The fields are empty.\n" +
                        "Please make sure you fill the fields.", ButtonType.OK);
                a.showAndWait();
                status = false;
            }
        if (!passwordTextField.getText().equals(confirmPasswordTextField.getText())) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Password doesn't match.\n" +
                    "Please try again.", ButtonType.OK);
            passwordTextField.setText("");
            confirmPasswordTextField.setText("");
            a.showAndWait();
            status = false;
        }
        DBConnection db = new DBConnection(DBConnection.ConnectionType.LOGIN_PROCESS);
        if (db.usernameExists(userNameTextField.getText())) {
            Alert a = new Alert(Alert.AlertType.WARNING, "'Account ID' already taken.\n" +
                    "Choose a different one.", ButtonType.OK);
            a.showAndWait();
            status = false;
        }
        if (status){
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            dbConnection.addEmployee(userNameTextField.getText(),firstNameTextField.getText(),lastNameTextField.getText(),
                    emailTextField.getText(),phoneNbrTextField.getText(),
                    roleTextField.getText(),passwordTextField.getText());

        }


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
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        complainPeople = db.getComplain();
    }

    private void setTfNumberOfComplains() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        tfNumberOfComplains.setText(String.valueOf(db.getNumberOfComplains()));
    }

    private void setDateAndMessage() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.setDateAndMessage(String.valueOf(userNameChoiceBox.getValue()), tfDateOfComplain, complainMessagetextArea);
    }
}


