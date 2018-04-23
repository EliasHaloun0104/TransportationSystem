package com.github.controller;

import com.github.model.DBConnection;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class ViewProfile {


    public void editButtonPressed(TextField userNameTextField,TextField firstNameTextField,TextField lastNameTextField,
                                  TextField phoneNbrTextField,TextField newPasswordTextField,
                                  TextField confirmPasswordTextField){

        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(userNameTextField,firstNameTextField,lastNameTextField,
                phoneNbrTextField,newPasswordTextField,confirmPasswordTextField
            ));
        for (TextField t: textFields) {
            t.setDisable(false);
            t.setEditable(true);
        }
    }
    public void saveButtonPressed(TextField userNameTextField,TextField firstNameTextField,TextField lastNameTextField,
                                  TextField phoneNbrTextField,TextField newPasswordTextField,
                                  TextField confirmPasswordTextField){
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(userNameTextField,firstNameTextField,lastNameTextField,
                phoneNbrTextField,newPasswordTextField,confirmPasswordTextField
                ));
        for (TextField t: textFields) {
            t.setDisable(true);
            t.setEditable(false);
        }
    }
    public void signOutButtonPressed(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sign out!");
        alert.setHeaderText("Do you wish to sign out");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get()==ButtonType.OK){
            StageManager.getInstance().showLogin();
        }
    }

}
