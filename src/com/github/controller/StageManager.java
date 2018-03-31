package com.github.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StageManager {
    private static StageManager stageManager = new StageManager();
    private Stage login;
    private Stage stage1;
    private Stage stage2;
    private Stage taxiInterface;

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        login = new Stage();
        stage1 = new Stage();
        stage2 = new Stage();
        taxiInterface = new Stage();
        try {
            // login
            Parent root = FXMLLoader.load(getClass().getResource("/com/github/view/login.fxml"));
            login.setTitle("Login");
            login.setScene(new Scene(root, 300, 400));
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

        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
