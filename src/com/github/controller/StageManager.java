package com.github.controller;

import com.github.model.SMS_Manager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class StageManager {
    private static StageManager stageManager = new StageManager();

    private Stage login;
    private Stage developerMenu;
    private Stage simulation;
    private Stage splashScreen;
    private Stage adminScreen;
    private Stage taxiScreen;
    private Stage driverScreen;
    private Stage userScreen;

    private ArrayList<Stage> stages;



    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {
        stages = new ArrayList<>();
    }

    public void hideAllOpen(){
        for (Stage s: stages) {
                s.hide();
        }
    }

    public Stage getLogin() {
        if(login == null){
            login = createStage("login.fxml");
        }
        return login;
    }

    public Stage getDeveloperMenu() {
        if(developerMenu == null){
            developerMenu = createStage("developerMenu.fxml");
        }
        return developerMenu;
    }

    public Stage getSimulation() {
        if(simulation == null){
            simulation = createStage("simulation.fxml");
            stages.add(simulation);
        }
        hideAllOpen();
        return simulation;
    }

    public Stage getSplashScreen() {
        if(splashScreen == null){
            splashScreen = createStage("splashScreen.fxml");
            stages.add(splashScreen);
        }
        hideAllOpen();
        return splashScreen;
    }

    public Stage getAdminScreen() {
        if(adminScreen == null){
            adminScreen = createStage("adminScreen.fxml");
            stages.add(adminScreen);
        }
        hideAllOpen();
        return adminScreen;
    }

    public Stage getTaxiScreen() {
        if(taxiScreen == null){
            taxiScreen = createStage("taxiDriver.fxml");
            stages.add(taxiScreen);
        }
        hideAllOpen();
        return taxiScreen;
    }

    public Stage getDriverScreen() {
        if(driverScreen == null){
            driverScreen = createStage("driver.fxml");
            stages.add(driverScreen);
        }
        hideAllOpen();
        return driverScreen;
    }

    public Stage getUserScreen() {
        if(userScreen == null){
            userScreen = createStage("userScreen.fxml");
            stages.add(userScreen);
        }
        hideAllOpen();
        return userScreen;
    }

    public Stage createStage(String stageName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/github/view/" + stageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        return stage;
    }

}
