package com.github.controller;

import com.github.model.DBConnection;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class LoginController {

    @FXML private Button exitLoginButton;
    @FXML private Pane loginPane, registrationPane, passwordPane, resetPasswordPane;
    @FXML private TextField tfFirstName, tfLastName, tfUsernameReg, tfEmailReg;
    @FXML private Label newUserMsgLabel, resetPasswordMsgLabel, passwordDetailsLabel;

    public void initialize() {
        // exit app button animation
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), exitLoginButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        exitLoginButton.setOnMouseEntered(e -> rotation.play());
    }

    // LOGIN PANE
    @FXML
    private void handleExitAppButton() {
        Platform.exit();
    }

    // fades in new user pane and fades out login pane
    @FXML
    private void handleNewUserButton() {
        newUserMsgLabel.setText("If you require an account with special access contact helpdesk");
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

    // fades in forgot password pane and fades out login pane
    @FXML
    private void handleForgotPasswordButton() {
        resetPasswordPane.setVisible(true);
        resetPasswordPane.setOpacity(0);
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(loginPane.opacityProperty(), 0);
        KeyValue kv2 = new KeyValue(resetPasswordPane.opacityProperty(), 1);
        KeyFrame kf = new KeyFrame(Duration.millis(1000), kv1, kv2);
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(1);
        timeline.play();
    }

    // RESET PASSWORD PANE
    @FXML
    private void handleResetPasswordNextButton() {
//        resetPasswordMsgLabel.setText("Further instructions email sent to\nblabla");
    }

    // fade out reset password and fade in login pane
    @FXML
    private void handleExitResetPasswordButton() {
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(loginPane.opacityProperty(), 1);
        KeyValue kv2 = new KeyValue(resetPasswordPane.opacityProperty(), 0);
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
            resetPasswordPane.setVisible(false);
        }).start();
        resetPasswordMsgLabel.setText("");
    }

    // SIGN UP PANE
    // account details
    @FXML
    private void handleRegistrationPaneNextButton() {

        if (validateFirstName() && validateLastName() && validateUsername() && validateEmail()) {
            passwordDetailsLabel.setText("An email was sent to " + tfEmailReg.getText() + " \nwith the confirmation code.");
            passwordPane.setVisible(true);
            passwordPane.setOpacity(0);
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(passwordPane.opacityProperty(), 1);
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

    private boolean validateFirstName() {
        boolean ok = true;
        if (tfFirstName.getText().isEmpty()) {
            newUserMsgLabel.setText("'First Name' is a mandatory field");
            ok = false;
        } else if (!tfFirstName.getText().matches("\\p{L}+")) {
            newUserMsgLabel.setText("Invalid characters in 'First Name' field\nOnly letters are accepted");
            ok = false;
        }
        return ok;
    }

    private boolean validateLastName() {
        boolean ok = true;
        if (tfLastName.getText().isEmpty()) {
            newUserMsgLabel.setText("'Last Name' is a mandatory field");
            ok = false;
        } else if (!tfLastName.getText().matches("\\p{L}+")) {
            newUserMsgLabel.setText("Invalid characters in 'Last Name' field\nOnly letters are accepted");
            ok = false;
        }
        return ok;
    }

    private boolean validateUsername() {
        boolean ok = true;
        if (tfUsernameReg.getText().isEmpty()) {
            newUserMsgLabel.setText("'Username' is a mandatory field");
            ok = false;
        } else {
             DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
             if (db.usernameExists(tfUsernameReg.getText())) {
                 newUserMsgLabel.setText("'Username' is already taken.");
                 ok = false;
             }
        }
        return ok;
    }

    private boolean validateEmail() {
        // checking for a good regex but...
        // not so important because to setup a password user needs
        // to check his email for confirmation code (avoiding fake emails...)

        // check db for existing email
        boolean ok = true;
        if (tfEmailReg.getText().isEmpty()) {
            newUserMsgLabel.setText("'Email' is a mandatory field");
            ok = false;
        } else {
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
            if (db.emailExists(tfUsernameReg.getText())) {
                newUserMsgLabel.setText("'Email' is already taken.");
                ok = false;
            }
        }
        return ok;
    }

    // fades in login pane and fades out new user pane
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

    // set up password
    // fades in login pane and fades out password pane
    @FXML
    private void handleExitPasswordPaneButton() {
        Timeline timeline = new Timeline();
        KeyValue kv1 = new KeyValue(loginPane.opacityProperty(), 1);
        KeyValue kv2 = new KeyValue(passwordPane.opacityProperty(), 0);
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
            passwordPane.setVisible(false);
        }).start();
    }
}
