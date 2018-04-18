package com.github.controller;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;


public class ViewProfile {
    private Tab tab;
    private Button editButton,saveButton;
    private TextField userNameTextField,firstNameTextField,lastNameTextField,
        emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
        createdDateTextField;

    public ViewProfile() {
        tab = new Tab();
        tab.setText("View Profile");
        userNameTextField = new TextField();
        firstNameTextField = new TextField ();
        lastNameTextField  = new TextField ();
        emailTextField = new TextField ();
        phoneNbrTextField = new TextField ();
        roleTextField = new TextField ();
        newPasswordTextField = new PasswordField ();
        confirmPasswordTextField = new PasswordField ();
        createdDateTextField = new TextField ();
        editButton = new Button("Edit");
        editButton.setOnAction((Event)->editButtonPressed());
        saveButton  = new Button("Save");
        saveButton.setOnAction((Event)->saveButtonPressed());



        VBox vBox_1 = new VBox();
        vBox_1.getChildren().addAll(
            new Label("User Name"),
            new Label("First Name"),
            new Label("Last Name"),
            new Label("E-mail"),
            new Label("Phone Number"),
            new Label("Role"),
            new Label("New Password"),
            new Label("Confirm Password"),
            new Label("Created On"),
            userNameTextField,firstNameTextField,lastNameTextField,
            emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
            createdDateTextField,
            editButton,saveButton
        );
        tab.setContent(vBox_1);
        saveButtonPressed();

    }

    public Tab getTab() {
        return tab;
    }





    private void editButtonPressed(){
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(userNameTextField,firstNameTextField,lastNameTextField,
            emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
            createdDateTextField));
        for (TextField t: textFields) {
            t.setDisable(false);
            t.setEditable(true);
        }
    }
    private void saveButtonPressed(){
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(userNameTextField,firstNameTextField,lastNameTextField,
            emailTextField,phoneNbrTextField,roleTextField,newPasswordTextField,confirmPasswordTextField,
            createdDateTextField));
        for (TextField t: textFields) {
            t.setDisable(true);
            t.setEditable(false);
        }
    }
}
