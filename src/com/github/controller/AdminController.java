package com.github.controller;

import com.github.model.DBConnection;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AdminController {
    @FXML
    private Button signOutButton;
    @FXML
    private VBox textFieldsWrapper;

    @FXML
    private Tab complainsTab, viewBookingTab, employeeTab;
    @FXML
    JFXTreeTableView<Employee> employeeTreeView;

    @FXML
    JFXTextField employeeSearchTextField,employeeUsernameTextField,employeeFirstNameTextField,employeeLastNameTextField,
            employeeEmailTextField,employeePhoneNbrTextField,employeeRoleTextField;
    @FXML
    JFXButton searchEmployeeButton,createEmployeeButton,updateEmployeeButton,deleteEmployeeButton;

    @FXML
    private JFXTreeTableView<Booking> treeView;

    @FXML
    private JFXTextField searchBookingTextField;

    @FXML
    private JFXButton deleteBookingButton, printBookingButton, printAllBookingsButton;

    @FXML
    JFXTreeTableView<Complaint> complaintTreeView;

    @FXML
    private JFXCheckBox handledComplaintCheckBox, notHandledComplaintCheckBox;

    @FXML
    private JFXTextField enterComplaintIdTextField, complaintIdToPrintTextField;

    @FXML
    private JFXTextArea complaintMessageTextArea, complaintAnswerTextArea;

    @FXML
    private JFXButton searchComplaintsButton, compensateButton, handleComplaintButton,
            loadComplaintButton, printComplaintButton, printAllComplaintsButton;


    private String status = null;


    public void initialize() {
        ExtendedButton.setFunction(signOutButton, ExtendedButton.Type.TO_LOGIN);
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
    private void employeeTabSelected(){
        loadEmployees("select * from Account where ROLE not like 'USER'");

    }
    @FXML
    private void searchEmployeeButtonPressed(){
        employeeUsernameTextField.setText("");
        employeeFirstNameTextField.setText("");
        employeeLastNameTextField.setText("");
        employeeEmailTextField.setText("");
        employeePhoneNbrTextField.setText("");
        employeeRoleTextField.setText("");

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.getEmployeeInfo(employeeSearchTextField.getText(),employeeUsernameTextField,employeeFirstNameTextField,employeeLastNameTextField,
                employeeEmailTextField,employeePhoneNbrTextField,employeeRoleTextField);

        employeeSearchTextField.setText("");

    }
    @FXML
    private void createEmployeeButtonPressed(){
        boolean status = true;

        if (employeeUsernameTextField.getText().trim().isEmpty() || employeeFirstNameTextField.getText().trim().isEmpty() ||
                employeeLastNameTextField.getText().trim().isEmpty() || employeePhoneNbrTextField.getText().trim().isEmpty() ||
                employeeRoleTextField.getText().trim().isEmpty() || employeeEmailTextField.getText().trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "The fields are empty.\n" +
                    "Please make sure you fill the fields.", ButtonType.OK);
            a.showAndWait();
            status = false;
        }

        DBConnection db = new DBConnection(DBConnection.ConnectionType.LOGIN_PROCESS);
        if (db.usernameExists(employeeUsernameTextField.getText())) {
            Alert a = new Alert(Alert.AlertType.WARNING, "'Account ID' already taken.\n" +
                    "Choose a different one.", ButtonType.OK);
            a.showAndWait();
            employeeUsernameTextField.setText("");
            status = false;

        }
        if (status) {
            DBConnection dbConnection = new DBConnection(DBConnection.ConnectionType.ADMIN);
            dbConnection.addEmployee(employeeUsernameTextField.getText(), employeeFirstNameTextField.getText(), employeeLastNameTextField.getText(),
                    employeeEmailTextField.getText(), employeePhoneNbrTextField.getText(),
                    employeeRoleTextField.getText());

            loadEmployees("Select * from Account");
            employeeUsernameTextField.setText("");
            employeeFirstNameTextField.setText("");
            employeeLastNameTextField.setText("");
            employeeEmailTextField.setText("");
            employeePhoneNbrTextField.setText("");
            employeeRoleTextField.setText("");

        }


    }
    @FXML
    private void updateEmployeeButtonPressed(){

    }
    @FXML
    private void deleteEmployeeButtonPressed(){

    }

    @FXML
    private void viewBookingTabSelected() {

        loadBookings("SELECT BookingId,Account_Username,Station_From,Station_To,Route_Id,AMOUNT,Date FROM Booking");
    }

    @FXML
    private void complainsTabSelected() {
        loadCompalaints("Select * from Complaint");


    }

    @FXML
    void loadComplaintButtonPressed(ActionEvent event) {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.getComplaintMessageAndAnswer(enterComplaintIdTextField.getText(), complaintMessageTextArea, complaintAnswerTextArea);
        compensateButton.setDisable(false);

    }

    @FXML
    private void notHandledComplaintCrossed() {
        handledComplaintCheckBox.setSelected(false);
        status = "Select * from Complaint where complaintStatus = 'Not Handled'";
        System.out.println(status);
        searchComplaintsButton.setDisable(false);

    }

    @FXML
    private void handledComplaintCrossed() {
        notHandledComplaintCheckBox.setSelected(false);
        status = "Select * from Complaint where complaintStatus = 'Handled'";
        System.out.println(status);
        searchComplaintsButton.setDisable(false);

    }

    @FXML
    void searchComplaintsButtonPressed(ActionEvent event) {
        loadCompalaints(status);

    }

    @FXML
    private void handleComplaintButtonPressed() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateComplainAnswer(complaintAnswerTextArea.getText(), enterComplaintIdTextField.getText());

        complaintMessageTextArea.setText("");
        complaintAnswerTextArea.setText("");
        enterComplaintIdTextField.setText("");
        loadCompalaints("Select * from Complaint");

    }

    @FXML
    private void compensateButtonPressed() {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db.updateComplainAnswer(complaintAnswerTextArea.getText(), enterComplaintIdTextField.getText());

        DBConnection db1 = new DBConnection(DBConnection.ConnectionType.ADMIN);
        String username = db1.getComplaintUsername(enterComplaintIdTextField.getText());

        DBConnection db2 = new DBConnection(DBConnection.ConnectionType.ADMIN);
        db2.setBalance(50, "Deposit", username);


        complaintMessageTextArea.setText("");
        complaintAnswerTextArea.setText("");
        enterComplaintIdTextField.setText("");
        loadCompalaints("Select * from Complaint");


    }

    @FXML
    private void printComplaintButtonPressed(ActionEvent event) {
        loadCompalaints("Select * from Complaint where ComplaintID ='" + complaintIdToPrintTextField.getText() + "'");
        print(complaintTreeView, event);
    }

    @FXML
    private void printAllComplaintsButtonPressed(ActionEvent event) {
        print(complaintTreeView, event);
    }


    @FXML
    private void printAllBookingsButtonPressed(ActionEvent event) {
        print(treeView, event);
    }

    @FXML
    private void printBookingButtonPressed(ActionEvent event) {
        loadBookings("SELECT BookingId,Account_Username,Station_From,Station_To,Route_Id,AMOUNT,Date " +
                "from Booking where BookingId ='" + searchBookingTextField.getText() + "'");
        print(treeView, event);
    }

    private <T extends RecursiveTreeObject<T>> void print(JFXTreeTableView<T> tree, ActionEvent event) {
        int count = tree.currentItemsCountProperty().getValue();
        if (event.getSource().toString().toLowerCase().contains("booking")) {
            System.out.println("print booking");
            Booking[] bookingList = new Booking[count];
            for (int i = 0; i < count; i++) {
                Booking booking = new Booking(tree.getColumns().get(0).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(1).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(2).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(3).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(4).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(5).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(6).getCellObservableValue(i).getValue().toString());
                bookingList[i] = booking;
            }
            try {
                new Booking().printToPdf(bookingList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (event.getSource().toString().toLowerCase().contains("complaint")) {
            System.out.println("print complaint");
            Complaint[] complaintList = new Complaint[count];
            for (int i = 0; i < count; i++) {
                Complaint complaint = new Complaint(tree.getColumns().get(0).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(1).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(2).getCellObservableValue(i).getValue().toString(),
                        tree.getColumns().get(3).getCellObservableValue(i).getValue().toString());
                complaintList[i] = complaint;
            }
            try {
                new Complaint().printToPdf(complaintList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void deleteBookingButtonPressed(ActionEvent event) {
    }

    private void loadEmployees(String sql){
        JFXTreeTableColumn<Employee, String> employee_username = new JFXTreeTableColumn<>("Username");
        employee_username.setPrefWidth(60);
        employee_username.setCellValueFactory(e -> e.getValue().getValue().username);

        JFXTreeTableColumn<Employee, String> employee_firstName = new JFXTreeTableColumn<>("First name");
        employee_firstName.setPrefWidth(60);
        employee_firstName.setCellValueFactory(e -> e.getValue().getValue().firstName);

        JFXTreeTableColumn<Employee, String> employee_lastName = new JFXTreeTableColumn<>("Last name");
        employee_lastName.setPrefWidth(60);
        employee_lastName.setCellValueFactory(e -> e.getValue().getValue().lastName);

        JFXTreeTableColumn<Employee, String> employee_email = new JFXTreeTableColumn<>("Email");
        employee_email.setPrefWidth(60);
        employee_email.setCellValueFactory(e -> e.getValue().getValue().email);

        JFXTreeTableColumn<Employee, String> employee_phone = new JFXTreeTableColumn<>("Phone");
        employee_phone.setPrefWidth(60);
        employee_phone.setCellValueFactory(e -> e.getValue().getValue().phone);

        JFXTreeTableColumn<Employee, String> employee_role = new JFXTreeTableColumn<>("Role");
        employee_role.setPrefWidth(60);
        employee_role.setCellValueFactory(e -> e.getValue().getValue().role);

        JFXTreeTableColumn<Employee, String> employee_date = new JFXTreeTableColumn<>("Date");
        employee_date.setPrefWidth(130);
        employee_date.setCellValueFactory(e -> e.getValue().getValue().creationDate);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Employee> root = new RecursiveTreeItem<>(db.getEmployee(sql), RecursiveTreeObject::getChildren);

        employeeTreeView.getColumns().setAll(employee_username, employee_firstName, employee_lastName, employee_email,employee_phone,
                employee_role,employee_date);

        employeeTreeView.setRoot(root);
        employeeTreeView.setShowRoot(false);

    }

    private void loadCompalaints(String sql) {
        JFXTreeTableColumn<Complaint, String> complaint_id = new JFXTreeTableColumn<>("Id");
        complaint_id.setPrefWidth(40);
        complaint_id.setCellValueFactory(e -> e.getValue().getValue().id);

        JFXTreeTableColumn<Complaint, String> complaint_username = new JFXTreeTableColumn<>("Username");
        complaint_username.setPrefWidth(90);
        complaint_username.setCellValueFactory(e -> e.getValue().getValue().username);

        JFXTreeTableColumn<Complaint, String> complaint_date = new JFXTreeTableColumn<>("Date");
        complaint_date.setPrefWidth(130);
        complaint_date.setCellValueFactory(e -> e.getValue().getValue().date);

        JFXTreeTableColumn<Complaint, String> complaint_isHandled = new JFXTreeTableColumn<>("Status");
        complaint_isHandled.setPrefWidth(100);
        complaint_isHandled.setCellValueFactory(e -> e.getValue().getValue().isHandled);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Complaint> root = new RecursiveTreeItem<>(db.getComplaints(sql), RecursiveTreeObject::getChildren);

        complaintTreeView.getColumns().setAll(complaint_id, complaint_username, complaint_date, complaint_isHandled);
        complaintTreeView.setRoot(root);
        complaintTreeView.setShowRoot(false);
    }


    private void loadBookings(String sql) {
        JFXTreeTableColumn<Booking, String> booking_id = new JFXTreeTableColumn<>("Id");
        booking_id.setPrefWidth(30);
        booking_id.setCellValueFactory(e -> e.getValue().getValue().bookingId);

        JFXTreeTableColumn<Booking, String> booking_username = new JFXTreeTableColumn<>("Username");
        booking_username.setPrefWidth(90);
        booking_username.setCellValueFactory(e -> e.getValue().getValue().accountUserName);

        JFXTreeTableColumn<Booking, String> booking_fromStation = new JFXTreeTableColumn<>("From");
        booking_fromStation.setPrefWidth(50);
        booking_fromStation.setCellValueFactory(e -> e.getValue().getValue().fromStation);

        JFXTreeTableColumn<Booking, String> booking_ToStation = new JFXTreeTableColumn<>("To");
        booking_ToStation.setPrefWidth(50);
        booking_ToStation.setCellValueFactory(e -> e.getValue().getValue().toStation);

        JFXTreeTableColumn<Booking, String> booking_routeID = new JFXTreeTableColumn<>("Route Id");
        booking_routeID.setPrefWidth(100);
        booking_routeID.setCellValueFactory(e -> e.getValue().getValue().routId);

        JFXTreeTableColumn<Booking, String> booking_amount = new JFXTreeTableColumn<>("Amount");
        booking_amount.setPrefWidth(70);
        booking_amount.setCellValueFactory(e -> e.getValue().getValue().amount);

        JFXTreeTableColumn<Booking, String> booking_date = new JFXTreeTableColumn<>("Date");
        booking_date.setPrefWidth(130);
        booking_date.setCellValueFactory(e -> e.getValue().getValue().date);

        DBConnection db = new DBConnection(DBConnection.ConnectionType.ADMIN);

        final TreeItem<Booking> root = new RecursiveTreeItem<>(db.getBookings(sql), RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(booking_id, booking_username, booking_fromStation, booking_ToStation, booking_routeID,
                booking_amount, booking_date);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }


}


