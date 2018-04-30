package com.github.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

    public static StageManager getInstance() {
        return stageManager;
    }

    public void switchStage(Stage toShow, Stage toHide){
        toShow.show();
        toHide.hide();
    }



    private StageManager() {
        try {
            developerMenu = createStage("developerMenu.fxml", "Developer Menu");
            simulation = createStage("simulation.fxml", "stage_2");
            userScreen = createStage("userScreen.fxml", "userScreen");
            splashScreen = createStage("splashScreen.fxml","");
            adminScreen = createStage("adminScreen.fxml","");
            taxiScreen = createStage("taxiDriver.fxml","");
            driverScreen = createStage("driver.fxml","");
            login = createStage("login.fxml","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getLogin() {
        return login;
    }

    public Stage getDeveloperMenu() {
        return developerMenu;
    }

    public Stage getSimulation() {
        return simulation;
    }

    public Stage getSplashScreen() {
        return splashScreen;
    }

    public Stage getAdminScreen() {
        return adminScreen;
    }

    public Stage getTaxiScreen() {
        return taxiScreen;
    }

    public Stage getDriverScreen() {
        return driverScreen;
    }

    public Stage getUserScreen() {
        return userScreen;
    }

    public Stage createStage(String stageName, String title) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/com/github/view/" + stageName));
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.TRANSPARENT);
        return stage;
    }


    public void showLogin() {
        login.show();
        splashScreen.hide();
        adminScreen.hide();
        taxiScreen.hide();
        driverScreen.hide();
    }

    public void showStage1() {
        developerMenu.show();
        simulation.hide();
    }

}
