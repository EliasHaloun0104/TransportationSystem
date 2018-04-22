package com.github.controller;

import com.github.model.Account;
import com.github.model.DBConnection;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Properties;

public class LoginController {

    @FXML private Button exitLoginButton;
    @FXML private Pane loginPane, registrationPane, passwordPane, resetPasswordPane;
    @FXML private TextField tfAccountLogin, tfFirstName, tfLastName, tfUsernameReg, tfPhoneReg, tfEmailReg, tfAccountPass, tfEmailReset;
    @FXML private PasswordField pfPasswordLogin, pfPasswordPass, pfPasswordConfirm, pfConfirmationCode;
    @FXML private Label newUserMsgLabel, resetPasswordMsgLabel, passwordDetailsLabel;

    public void initialize() {
        exitButtonAnimation();
    }

    // LOGIN PANE
    private void exitButtonAnimation() {
        RotateTransition rotation = new RotateTransition(Duration.seconds(0.5), exitLoginButton);
        rotation.setCycleCount(1);
        rotation.setByAngle(360);
        exitLoginButton.setOnMouseEntered(e -> rotation.play());
    }

    @FXML
    private void handleExitAppButtonPressed() {
        StageManager.getInstance().getLogin().hide();
    }

    @FXML
    private void loginButtonPressed() {
        if (validateLogin(tfAccountLogin.getText(), pfPasswordLogin.getText())) {
            // TODO: needs to check the role in db to load the correct scene
            tfAccountLogin.setText("");
            pfPasswordLogin.setText("");
            StageManager.getInstance().setEmployeeMenu();
        } else {
            invalidLogin();
        }
    }

    private void invalidLogin() {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Invalid login.\nCheck your Account ID and/or password.", ButtonType.OK);
        a.showAndWait();
    }

    private boolean validateLogin(String account, String password) {
        // TODO: create new connection type
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
        return  db.validateLogin(account, password);
    }

    // fades in new user registration pane and fades out login pane
    @FXML
    private void handleNewUserButtonPressed() {
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
    private void handleForgotPasswordButtonPressed() {
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
    private void handleResetPasswordNextButtonPressed() {
        if (!tfEmailReset.getText().trim().isEmpty() && validateEmail(tfEmailReset.getText())) {
            passwordDetailsLabel.setText("Check your email account for the confirmation code.");

            // create random confirmation code and add it to database
            String confirmationCode = generateConfirmationCode();
            sendConfirmationCodeEmail(tfEmailReset.getText(), confirmationCode);

            DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
            db.addConfirmationCode(tfEmailReset.getText(), confirmationCode);

            // fade in pane to set new password
            passwordPane.setVisible(true);
            passwordPane.setOpacity(0);
            Timeline timeline = new Timeline();
            KeyValue kv1 = new KeyValue(passwordPane.opacityProperty(), 1);
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

        } else {
            invalidEmail();
        }
    }

    private String generateConfirmationCode() {
        SecureRandom random = new SecureRandom();
        String confirmationCode = "";

        for (int i = 0; i < 8; i++) {
            confirmationCode += random.nextInt(9);
        }

        return confirmationCode;
    }

    private void invalidEmail() {
        Alert a = new Alert(Alert.AlertType.INFORMATION, "Invalid email address.", ButtonType.OK);
        a.showAndWait();
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

    // NEW ACCOUNT REGISTRATION PANE
    @FXML
    private void handleRegistrationPaneNextButtonPressed() {

        if (validateFirstName() && validateLastName() && validateUsername() && !validateEmail(tfEmailReg.getText())) {

            // create random confirmation code
            String confirmationCode =  generateConfirmationCode();

            String accountId = tfUsernameReg.getText();
            String firstName = tfFirstName.getText();
            String lastName = tfLastName.getText();
            String email = tfEmailReg.getText();
            String phone = tfPhoneReg.getText();

            Account.getInstance().setAccountId(accountId);
            Account.getInstance().setFirstName(firstName);
            Account.getInstance().setLastName(lastName);
            Account.getInstance().setEmail(email);
            Account.getInstance().setPhone(phone);
            Account.getInstance().setConfirmationCode(confirmationCode);

            // add user to db and send email with confirmation code to setup password
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);

            // TODO: needs better handling
            // account should only be added if email is succesfully sent...
            if (db.addUser(accountId, firstName, lastName, email, phone, confirmationCode)) {
                sendConfirmationCodeEmail(email, confirmationCode);

                // fade out registration pane and fade in password pane
                passwordDetailsLabel.setText("Check your email account for the confirmation code.");
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
    }

    // TODO: review validations....
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

    private boolean validateEmail(String email) {
        // checking for a good regex but...
        // not so important because to setup a password user needs
        // to check his email for confirmation code (avoiding fake emails...)

        // check db for existing email
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
        return db.emailExists(email);
    }

    private void sendConfirmationCodeEmail(String email, String confirmationCode) {

        Properties prop = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("resources/files/db.properties")) {
            prop.load(in);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(prop.getProperty("sendEmailUsername"), prop.getProperty("sendEmailPassword"));
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(prop.getProperty("sendEmailUsername")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Westeros Traffic: Confirmation code");
            message.setText("Use the following confirmation code to complete your account creation and setup your password: " + confirmationCode);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // fades in login pane and fades out registration pane
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

    // PASSWORD PANE
    @FXML
    private void handleFinishButtonPressed() {
        String account = tfAccountPass.getText();
        String confirmationCode = pfConfirmationCode.getText();
        String password = pfPasswordPass.getText();
        String passwordConfirmation = pfPasswordConfirm.getText();

        // TODO: add password confirmation validation!

        if (validateConfirmationCode(account, confirmationCode)) {
            System.out.println("ok");
            DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
            if (db.setupPassword(account, password)) {
                passwordDetailsLabel.setText("Account is setup!");
            }
        } else {
            passwordDetailsLabel.setText("Invalid confirmation code or account ID");
        }
    }

    private boolean validateConfirmationCode(String account, String confirmationCode) {
        DBConnection db = new DBConnection(DBConnection.ConnectionType.ACCOUNT_SETUP);
        return db.validateConfirmationCode(account, confirmationCode);
    }

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
