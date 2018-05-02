package com.github.model;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
    // constructor needs a connection type argument
    public enum ConnectionType {
        ADMIN, LOGIN_PROCESS
    }

    private Connection c;
    private String url;
    private String user;
    private String password;

    public DBConnection(ConnectionType connectionType) {

        // load properties file
        Properties prop = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("resources/properties/db.properties")) {
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
        if (connectionType == ConnectionType.LOGIN_PROCESS) {
            url = prop.getProperty("database");
            user = prop.getProperty("userAccountSetup");
            password = prop.getProperty("passwordAccountSetup");
        }

        if (connectionType == ConnectionType.ADMIN) {
            url = prop.getProperty("database");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
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

        String query = "SELECT count(*) FROM Account WHERE userName = ?";
        int count = dbValidation(query, username);

        return count > 0;
    }

    public boolean emailExists(String email) {

        String query = "SELECT count(*) FROM Account WHERE email = ?";
        int count = dbValidation(query, email);

        return count > 0;
    }

    private int dbValidation(String query, String col) {
        int count = 0;
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, col);
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
        return count;
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

    public String getRole(String userName) {
        String query = "Select role from Account where userName = ?";
        String role = "";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    role = rs.getString(1);
                    System.out.println(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return role;
    }

    public ArrayList<String> getAccountDetails(String userName) {
        ArrayList<String> userDetails = new ArrayList<>();
        String query = "Select userName, firstName, lastName, email, phoneNumber, balance, role, creationDate from Account where userName = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    for (int i = 1; i <= 8; i++) {
                        userDetails.add(rs.getString(i));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return userDetails;
    }

    public void updateAccountDetails(String firstName, String lastName,
                                     String phoneNumber, String newPassword) {

        String query = "Update Account set firstName = ?, lastName= ?,phoneNumber = ?, password = ? where userName =?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, phoneNumber);
            ps.setString(4, newPassword);
            ps.setString(5, Account.getInstance().getAccountId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Station> getStations() {
        ArrayList<Station> stations = new ArrayList<>();
        String query = "SELECT * FROM TransportationSystem.Station";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stations.add(new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Query failed.");
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stations;
    }

    public RouteCalculate getRoutesSearched(String from, String to) {
        String query = "SELECT * FROM Route_Driver_Vehicle where IDSpecial IN (SELECT IDSpecial  FROM Route_Driver_Vehicle WHERE fromStation = ?) AND IDSpecial IN (SELECT IDSpecial  FROM Route_Driver_Vehicle WHERE toStation = ?)";
        RouteCalculate scheduledRoutes = new RouteCalculate();
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1,from);
            ps.setString(2,to);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    scheduledRoutes.addToList(rs.getInt(3),new ScheduledRoute(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTime(8), rs.getTime(9), rs.getTime(10), rs.getFloat(11), rs.getString(12), rs.getString(13), rs.getInt(14)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return scheduledRoutes;
    }

    public ArrayList<String> getAvailableDestination(String from) {
        String query = "SELECT AvailableDes FROM\n" +
            "(SELECT DISTINCT toStation AS AvailableDes" +
            " FROM Route_Driver_Vehicle" +
            " WHERE ID IN" +
            "   (SELECT DISTINCT ID FROM\n" +
            "      Route_Driver_Vehicle\n" +
            "    WHERE\n" +
            "      fromStation = ?\n" +
            "   )\n" +
            ")\n" +
            "AS Des WHERE AvailableDes !=  ?";
        ArrayList<String> availableDestination = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1,from);
            ps.setString(2,from);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    availableDestination.add(rs.getString(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return availableDestination;
    }


    public ArrayList<ScheduledRoute> getRoutesFFF() {
        String query = "SELECT * FROM TransportationSystem.Route_Driver_Vehicle";
        ArrayList<ScheduledRoute> scheduledRoutes = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    scheduledRoutes.add(new ScheduledRoute(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getTime(8), rs.getTime(9), rs.getTime(10), rs.getFloat(11), rs.getString(12), rs.getString(13),rs.getInt(14)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return scheduledRoutes;
    }

    public int getNumberOfComplains() {
        int numberOfComplains = 0;
        String query = "select count(*) from Complaint where isHandled = 0";

        try (PreparedStatement ps = c.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    numberOfComplains = rs.getInt(1);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfComplains;
    }

    public ArrayList<ComplainPerson> getComplain() {
        ArrayList<ComplainPerson> person = new ArrayList<>();

        String query = "select Account_userName, message, creationDate from Complaint where isHandled = 0";

        try (PreparedStatement ps = c.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    person.add(new ComplainPerson(rs.getString(1), rs.getString(2), rs.getString(3)));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return person;
    }

    public void setDateAndMessage(String userName, TextField date, TextArea message) {


        String query = "Select creationDate, message from Complaint where Account_userName = ?  and isHandled = 0";

        try (PreparedStatement ps = c.prepareStatement(query)) {

            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    date.setText(rs.getString(1));
                    message.setText(rs.getString(2));

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

    }

    public void updateComplainAnswer(String message, String userName) {
        String query = "Update Complaint set answer = ?, isHandled = 1 where Account_userName =?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, message);
            ps.setString(2, userName);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void addCompensationValue(String userName) {
        String query = "Update Account set balance = 100 where userName = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void addEmployee(String userName,String firstName,String lastName,String email,
                            String phoneNbr,String role, String password){
        String query = "INSERT INTO Account (userName, firstName, lastName, email, phoneNumber, password, role,confirmationCode ) VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = c.prepareStatement(query)) {

            SecureRandom random = new SecureRandom();
            String confirmationCode = "";
            for (int i = 0; i < 8; i++) {
                confirmationCode += random.nextInt(9);
            }
            ps.setString(1, userName);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.setString(5, phoneNbr);
            ps.setString(6, password);
            ps.setString(7, role);
            ps.setString(8,confirmationCode);


            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();

        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
