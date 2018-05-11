package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;

public class AdminController {

    @FXML
    private JFXTreeTableView<Booking> treeView;

    @FXML
    private JFXTextField searchTF;

    @FXML
    private JFXButton deleteButton, printButton, printAllButton;


    @FXML
    private TextField userNameTextField,firstNameTextField,lastNameTextField, emailTextField,phoneNbrTextField,
            roleTextField,passwordTextField,confirmPasswordTextField, vehicleNumberTextField;
    @FXML
    private Tab complainsTab, viewBookingTab;
    @FXML
    private Button signOutButton;
    @FXML
    private VBox textFieldsWrapper;

    @FXML JFXTreeTableView<Complaint> complaintTreeView;

    @FXML
    private JFXButton searchComplaintsButton;

    @FXML
    private JFXCheckBox handledCheckBox;

    @FXML
    private JFXCheckBox notHandledCheckBox;

    @FXML
    private JFXTextField enterUsernameTextField;

    @FXML
    private JFXTextArea messageTextArea;

    @FXML
    private JFXTextArea answerTextArea;

    @FXML
    private JFXButton compensateButton;

    @FXML
    private JFXButton loadComplaintButton;

    private String status = null;



    public void initialize() {
        ExtendedButton.setFunction(signOutButton,ExtendedButton.Type.TO_LOGIN);
        searchComplaintsButton.setDisable(true);
        compensateButton.setDisable(true);


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
    private void viewBookingTabSelected(){

        loadBookings("SELECT BookingId,Account_Username,Station_From,Station_To,Route_Id,AMOUNT,Date FROM Booking");
    }

    @FXML
    private void complainsTabSelected() {
       loadCompalaints("Select * from Complaint");


    }

    @FXML
    void loadComplaintButtonPressed(ActionEvent event) {
        DBConnection db =new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.getComplaintMessageAndAnswer(enterUsernameTextField.getText(),messageTextArea,answerTextArea);
        compensateButton.setDisable(false);

    }
    @FXML
    private void notHandledCrossed(){
        handledCheckBox.setSelected(false);
        status = "Select * from Complaint where complaintStatus = 'NotHandled'";
        System.out.println(status);
        searchComplaintsButton.setDisable(false);

    }
    @FXML
    private void handledCrossed(){
        notHandledCheckBox.setSelected(false);
        status = "Select * from Complaint where complaintStatus = 'Handled'";
        System.out.println(status);
        searchComplaintsButton.setDisable(false);

    }
    @FXML
    void searchComplaintsButtonPressed(ActionEvent event) {
            loadCompalaints(status);

    }

    @FXML
    private void compensateButtonPressed() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateComplainAnswer(answerTextArea.getText(),enterUsernameTextField.getText());

        DBConnection db1 = new DBConnection(DBConnection.ConnectionType.LOGIN_PROCESS);
        db1.setBalance(50,"Deposit", enterUsernameTextField.getText());

        messageTextArea.setText("");
        answerTextArea.setText("");
        enterUsernameTextField.setText("");


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
    @FXML
    private void printAllButtonPressed(ActionEvent event) {


    }

    @FXML
    private void printButtonPressed(ActionEvent event) {
    loadBookings("SELECT BookingId,Account_Username,Station_From,Station_To,Route_Id,AMOUNT,Date " +
            "from Booking where BookingId ='"+searchTF.getText()+"'");
    }

    @FXML
    private void deleteButtonPressed(ActionEvent event) {
    }


    private void loadCompalaints(String sql){
        JFXTreeTableColumn<Complaint,String> complaint_username=new JFXTreeTableColumn<>("Username");
        complaint_username.setPrefWidth(110);
        complaint_username.setCellValueFactory(e->e.getValue().getValue().username);

        JFXTreeTableColumn<Complaint,String> complaint_date=new JFXTreeTableColumn<>("Date");
        complaint_date.setPrefWidth(130);
        complaint_date.setCellValueFactory(e->e.getValue().getValue().date);

        JFXTreeTableColumn<Complaint,String> complaint_isHandled=new JFXTreeTableColumn<>("Status");
        complaint_isHandled.setPrefWidth(100);
        complaint_isHandled.setCellValueFactory(e->e.getValue().getValue().isHandled);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Complaint> root = new RecursiveTreeItem<>( db.getComplaints(sql),RecursiveTreeObject::getChildren);

        complaintTreeView.getColumns().setAll(complaint_username,complaint_date,complaint_isHandled);
        complaintTreeView.setRoot(root);
        complaintTreeView.setShowRoot(false);
    }


    private void loadBookings(String sql){
        JFXTreeTableColumn<Booking,String> booking_id=new JFXTreeTableColumn<>("Id");
        booking_id.setPrefWidth(30);
        booking_id.setCellValueFactory(e->e.getValue().getValue().bookingId);

        JFXTreeTableColumn<Booking,String> booking_username=new JFXTreeTableColumn<>("Username");
        booking_username.setPrefWidth(90);
        booking_username.setCellValueFactory(e->e.getValue().getValue().accountUserName);

        JFXTreeTableColumn<Booking,String> booking_fromStation=new JFXTreeTableColumn<>("From");
        booking_fromStation.setPrefWidth(50);
        booking_fromStation.setCellValueFactory(e->e.getValue().getValue().fromStation);

        JFXTreeTableColumn<Booking,String> booking_ToStation=new JFXTreeTableColumn<>("To");
        booking_ToStation.setPrefWidth(50);
        booking_ToStation.setCellValueFactory(e->e.getValue().getValue().toStation);

        JFXTreeTableColumn<Booking,String> booking_routeID=new JFXTreeTableColumn<>("Route Id");
        booking_routeID.setPrefWidth(100);
        booking_routeID.setCellValueFactory(e->e.getValue().getValue().routId);

        JFXTreeTableColumn<Booking,String> booking_amount=new JFXTreeTableColumn<>("Amount");
        booking_amount.setPrefWidth(70);
        booking_amount.setCellValueFactory(e->e.getValue().getValue().amount);

        JFXTreeTableColumn<Booking,String> booking_date=new JFXTreeTableColumn<>("Date");
        booking_date.setPrefWidth(130);
        booking_date.setCellValueFactory(e->e.getValue().getValue().date);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Booking> root = new RecursiveTreeItem<>(db.getBookings(sql), RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(booking_id,booking_username,booking_fromStation,booking_ToStation,booking_routeID,
                booking_amount,booking_date);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }


}


