package com.github.model;

import com.github.controller.StageManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    // constructor needs a connection type argument
    public enum ConnectionType {
        ACCOUNT_SETUP
    }

    private Connection c;
    private String url;
    private String user;
    private String password;

    public DBConnection(ConnectionType connectionType) {

        // load properties file
        Properties prop = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("resources/files/db.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ssl certificate config
        System.setProperty("javax.net.ssl.trustStore", System.getProperty("user.home")
                + System.getProperty("file.separator")
                + "myKeyStore");
        System.setProperty("javax.net.ssl.trustStorePassword", prop.getProperty("trustStorePassword"));

        // depending on connection type create correct access to db
        // db credentials
        if (connectionType == ConnectionType.ACCOUNT_SETUP) {
            url = prop.getProperty("database");
            user = prop.getProperty("userAccountSetup");
            password = prop.getProperty("passwordAccountSetup");
        }

        // db connection
        try {
            c = DriverManager.getConnection(url, user, password);
            System.out.println("It's working!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean usernameExists(String username) {
        int count = 0;
        String query = "SELECT count(*) FROM Account WHERE userName = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count > 0;
    }

    public boolean emailExists(String email) {
        int count = 0;
        String query = "SELECT count(*) FROM Account WHERE email = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count > 0;
    }

    public boolean addUser(String userName, String firstName, String lastName, String email, String phone, String confirmationCode) {
        boolean status = true;
        String query = "INSERT INTO Account (userName, firstName, lastName, email, phoneNumber, confirmationCode) VALUES (?, ?, ? ,?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, confirmationCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add user failed.");
            status = false;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public void addConfirmationCode(String email, String confirmationCode) {
        String addConfirmationCode = "UPDATE Account SET confirmationCode = ? WHERE email = ?";

        try (PreparedStatement ps = c.prepareStatement(addConfirmationCode)) {
            ps.setString(2, email);
            ps.setString(1, confirmationCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add confirmation code failed.");
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean setupPassword(String userName, String password) {
        boolean status = true;
        String query = "UPDATE Account SET password = ? WHERE userName = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, password);
            ps.setString(2, userName);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("set password failed.");
            status = false;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public boolean validateConfirmationCode(String userName, String confirmationCode) {
        int count = 0;
        String query = "SELECT count(*) FROM Account WHERE userName = ? && confirmationCode = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, confirmationCode);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count == 1;
    }

    public boolean validateLogin(String userName, String password) {
        int count = 0;
        String query = "SELECT count(*) FROM Account WHERE userName = ? && password = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("validate login failed.");
            ex.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count == 1;
    }

    public void loginToTheCorrespondingScene() {
        String query = "Select * from Account where userName = '" + Account.getInstance().getAccountId() + "'";
        String role ="";
        try {
            PreparedStatement preparedStatement = c.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                role = resultSet.getString(7);
                System.out.println(role);

                if (role.equals("Admin")) {
                    try {
                        StageManager.getInstance().switchStage(StageManager.getInstance().getAdminScrn(), StageManager.getInstance().getLogin());
                    } catch (Exception e) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else if (role.equals("Bus Driver")) {
                    try {
                        StageManager.getInstance().switchStage(StageManager.getInstance().getBusScrn(), StageManager.getInstance().getLogin());

                    } catch (Exception e) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);

                    }
                } else if (role.equals("Taxi Driver")) {
                    try {
                        StageManager.getInstance().switchStage(StageManager.getInstance().getTaxiScrn(), StageManager.getInstance().getLogin());

                    } catch (Exception e) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                    }
                } else if (role.equals("User")) {
                    try {

                        StageManager.getInstance().switchStage(StageManager.getInstance().getUserGUI(), StageManager.getInstance().getLogin());


                    } catch (Exception e) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);

                    }
                } else if (role.equals("Train Driver")) {
                    try {
                        StageManager.getInstance().switchStage(StageManager.getInstance().getTrainScrn(), StageManager.getInstance().getLogin());

                    } catch (Exception e) {
                        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, e);
                    }
                }

            }
        } catch (SQLException e) {

        }
    }
}
