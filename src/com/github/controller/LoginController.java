package com.github.controller;

//import com.github.model.DBConnection;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoginController {

    @FXML private Button exitLoginButton, exitRegistrationButton;
    @FXML private Pane loginPane, registrationPane;

    public void initialize() {
//        DBConnection db = new DBConnection();

        // exit button animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), exitLoginButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        exitLoginButton.setOnMouseEntered(e -> rotation.play());
    }

    @FXML
    private void handleExitButton() {
        Platform.exit();
    }

    @FXML
    private void handleNewUserButton() {
        registrationPane.setVisible(true);
        registrationPane.setOpacity(0);
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(loginPane.opacityProperty(), 0);
        KeyValue kv2 = new KeyValue(registrationPane.opacityProperty(), 1);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    private void handleExitRegistrationButton() {
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(loginPane.opacityProperty(), 1);
        KeyValue kv2 = new KeyValue(registrationPane.opacityProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(1);
        timeline.play();
        new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                registrationPane.setVisible(false);
        }).start();
    }
}
