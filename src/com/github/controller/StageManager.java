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
            createStage("stage1.fxml", "stage_1", stage_1);
            createStage("stage2.fxml", "stage_2", stage_2);
            createStage("taxiInterface.fxml", "taxiInterface", taxiInterface);
            createStage("userGUI.fxml", "userGUI", userGUI);


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

    public void createStage(String stageName, String title, Stage stage) throws IOException {
        Parent rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/" + stageName));
        Scene scene = new Scene(rootPrimary);
        scene.getStylesheets().add("com/github/view/style.css");
        stage.setTitle(title);
        stage.setScene(scene);
    }
}
