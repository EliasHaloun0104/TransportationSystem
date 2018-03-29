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
    private Stage stage_1;
    private Stage stage_2;
    private Stage taxiInterface;

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        stage_1 = new Stage();
        stage_2 = new Stage();
        taxiInterface = new Stage();
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
    public void showTaxiInterface(){
        stage_1.hide();
        taxiInterface.show();
    }
}
