package com.github.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StageManager {
    private static StageManager stageManager = new StageManager();
    private Stage stage_1;
    private Stage stage_2;
    private Stage stage_3;
    private Stage taxiInterface;
    private Stage userGUI;

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        stage_1 = new Stage();
        stage_2 = new Stage();
        stage_3 = new Stage();
        taxiInterface = new Stage();
        userGUI = new Stage();
        try {
            //Stage 1
            Parent rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/stage1.fxml"));
            Scene scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            stage_1.setTitle("List");
            stage_1.setScene(scene);
            //Stage 2
            rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/stage2.fxml"));
            scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            stage_2.setTitle("List");
            stage_2.setScene(scene);
            //Taxi Interface
            rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/taxiInterface.fxml"));
            scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            taxiInterface.setTitle("TAXI");
            taxiInterface.setScene(scene);
            //Stage 3
            rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/stage3.fxml"));
            scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            stage_3.setTitle("List");
            stage_3.setScene(scene);

            //UserGUI
            rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/userGUI.fxml"));
            scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            userGUI.setTitle("List");
            userGUI.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage_1() {
        stage_1.show();
        stage_2.hide();
    }

    public void showStage_2() {
        stage_2.show();
        stage_1.hide();
    }
    public void showStage_3() {
        stage_3.show();
        stage_1.hide();
    }
    public void showTaxiInterface(){
        stage_1.hide();
        taxiInterface.show();
    }
    public void showUserGUI(){
        stage_1.hide();
        userGUI.show();
    }
}
