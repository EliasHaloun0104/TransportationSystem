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
    private Stage splashScreen;
    private Stage adminScrn;
    private Stage employeeMenu;
    private Stage taxiScrn;
    private Stage busScrn;
    private Stage trainScrn;


    public static StageManager getInstance() {
        return stageManager;
    }

    private StageManager() {


        login = new Stage();
        stage1 = new Stage();
        stage2 = new Stage();
        taxiInterface = new Stage();
        splashScreen = new Stage();
        adminScrn = new Stage();
        employeeMenu = new Stage();
        taxiScrn = new Stage();
        busScrn = new Stage();
        trainScrn = new Stage();

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
            //SplashScreen
            root = FXMLLoader.load(getClass().getResource("/com/github/view/SplashScreen.fxml"));
            splashScreen.setScene(new Scene(root));
            splashScreen.initStyle(StageStyle.TRANSPARENT);
            //Admin
            root = FXMLLoader.load(getClass().getResource("/com/github/view/AdminLoginScrn.fxml"));
            adminScrn.setScene(new Scene(root));
            adminScrn.initStyle(StageStyle.TRANSPARENT);
            //EmployeeMenu
            root = FXMLLoader.load(getClass().getResource("/com/github/view/EmployeeMenu.fxml"));
            employeeMenu.setScene(new Scene(root));
            employeeMenu.initStyle(StageStyle.TRANSPARENT);
            //TaxiDriver
            root = FXMLLoader.load(getClass().getResource("/com/github/view/TaxiDriver.fxml"));
            taxiScrn.setScene(new Scene(root));
            taxiScrn.initStyle(StageStyle.TRANSPARENT);
            //TrainDriver
            root = FXMLLoader.load(getClass().getResource("/com/github/view/TrainDriver.fxml"));
            trainScrn.setScene(new Scene(root));
            trainScrn.initStyle(StageStyle.TRANSPARENT);
            //BusDriver
            root = FXMLLoader.load(getClass().getResource("/com/github/view/BusDriver.fxml"));
            busScrn.setScene(new Scene(root));
            busScrn.initStyle(StageStyle.TRANSPARENT);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setEmployeeMenu(){
        employeeMenu.show();
        login.hide();
    }
    public void setAdminScrn(){
        adminScrn.show();
        employeeMenu.hide();
    }
    public void setTaxiDriver(){
        taxiScrn.show();
        employeeMenu.hide();
    }
    public void setTrainDriver(){
        trainScrn.show();
        employeeMenu.hide();
    }
    public void setBusDriver(){
        busScrn.show();
        employeeMenu.hide();
    }
    public void setSplashScreen(){
        splashScreen.show();
    }

    public void showLogin() {
        login.show();
        splashScreen.hide();
        adminScrn.hide();
        taxiScrn.hide();
        trainScrn.hide();
        busScrn.hide();
    }

    public void showStage1() {
        stage1.show();
        stage2.hide();
    }

    public void showStage2() {
        stage2.show();
        stage1.hide();
    }
//    public void showStage_3() {
//        stage_3.show();
//        stage_1.hide();
//    }

    public void showTaxiInterface(){
        stage1.hide();
        taxiInterface.show();
    }

//    public void showUserGUI(){
//        stage_1.hide();
//        userGUI.show();
//    }
//
//    public void createStage(String stageName, String title, Stage stage) throws IOException {
//        Parent rootPrimary = FXMLLoader.load(getClass().getResource("/com/github/view/" + stageName));
//        Scene scene = new Scene(rootPrimary);
//        scene.getStylesheets().add("com/github/view/style.css");
//        stage.setTitle(title);
//        stage.setScene(scene);
//    }

}
