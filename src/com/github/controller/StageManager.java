package com.github.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageManager {
    private static StageManager stageManager = new StageManager();
    private Stage login;
    private Stage stage1;
    private Stage stage2;
    private Stage taxiInterface;
    private Stage userLoggedscrn;
    private Stage signOutWindow;
    private Stage viewProfil;

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        login = new Stage();
        stage1 = new Stage();
        stage2 = new Stage();
        taxiInterface = new Stage();
        userLoggedscrn = new Stage();
        signOutWindow = new Stage();
        viewProfil = new Stage();
        try {
            // login
            Parent root = FXMLLoader.load(getClass().getResource("/com/github/view/login.fxml"));
            Scene scene = new Scene(root, 350, 500);
            scene.setFill(Color.TRANSPARENT);
            login.setTitle("Login");
            login.setScene(scene);
            login.initStyle(StageStyle.TRANSPARENT);
            // stage1
            root = FXMLLoader.load(getClass().getResource("/com/github/view/stage1.fxml"));
            stage1.setTitle("List");
            stage1.setScene(new Scene(root));
            // stage2
            root = FXMLLoader.load(getClass().getResource("/com/github/view/stage2.fxml"));
            stage2.setTitle("List");
            stage2.setScene(new Scene(root));
            // taxi interface
            root = FXMLLoader.load(getClass().getResource("/com/github/view/taxiInterface.fxml"));
            taxiInterface.setTitle("TAXI");
            taxiInterface.setScene(new Scene(root));
            // logged in user stage
            root = FXMLLoader.load(getClass().getResource("/com/github/view/UserLoginScrn.fxml"));
            userLoggedscrn.setScene(new Scene(root));
            userLoggedscrn.initStyle(StageStyle.TRANSPARENT);
            // Sign out window
            root = FXMLLoader.load(getClass().getResource("/com/github/view/signOutScrn.fxml"));
            signOutWindow.setScene(new Scene(root));
            signOutWindow.initStyle(StageStyle.TRANSPARENT);
            signOutWindow.initModality(Modality.APPLICATION_MODAL);
            // View User Profil
            root = FXMLLoader.load(getClass().getResource("/com/github/view/ViewProfil.fxml"));
            viewProfil.setScene(new Scene(root));
            viewProfil.initStyle(StageStyle.TRANSPARENT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setSignOutWindow(){
        signOutWindow.show();
    }

    public void setUserLoggedscrn(){
        userLoggedscrn.show();
        login.hide();
    }

    public void showLogin() {
        login.show();
    }

    public void showStage1() {
        stage1.show();
        stage2.hide();
    }

    public void showStage2() {
        stage2.show();
        stage1.hide();
    }
    public void showTaxiInterface(){
        stage1.hide();
        taxiInterface.show();
    }
    public void setViewProfil(){
        viewProfil.show();
        userLoggedscrn.hide();
    }
}
