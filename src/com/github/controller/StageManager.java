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

    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        stage_1 = new Stage();
        stage_2 = new Stage();
        try {
            Path path = Paths.get("/com/github/view/stage1.fxml");
            Parent rootPrimary = FXMLLoader.load(getClass().getResource(path.toString()));
            Scene scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            stage_1.setTitle("List");
            stage_1.setScene(scene);
            stage_1.setOnCloseRequest(we -> {
                //InOut.writeToFile("Record.ser", Stage1Controller.items);
            });
            path = Paths.get("/com/github/view/stage2.fxml");
            rootPrimary = FXMLLoader.load(getClass().getResource(path.toString()));
            scene = new Scene(rootPrimary);
            scene.getStylesheets().add("com/github/view/style.css");
            stage_2.setTitle("List");
            stage_2.setScene(scene);
            stage_2.setOnCloseRequest(we -> {
                //InOut.writeToFile("Record.ser", Stage1Controller.items);
            });

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
}
