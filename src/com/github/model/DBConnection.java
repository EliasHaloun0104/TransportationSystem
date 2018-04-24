package com.github.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
    // constructor needs a connection type argument
    public enum ConnectionType {ACCOUNT_SETUP, ADMIN}
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
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean usernameExists(String username) {

        int count = 0;
        // temp table userTest (for testing...)
        String query = "SELECT count(*) FROM userTest WHERE accountID = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        }
        finally {
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
        // temp table userTest (for testing...)
        String query = "SELECT count(*) FROM userTest WHERE email = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Query failed.");
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count > 0;
    }


    public boolean addUser(String accountID, String firstName, String lastName, String email, String confirmationCode) {

        boolean status = true;
        String addUser = "INSERT INTO userTest (accountId, firstName, lastName, email) VALUES (?, ?, ? ,?)";
        String addConfirmationCode = "INSERT INTO passwordTest (accountId, confirmationCode) VALUES (?, ?)";

        try (PreparedStatement ps = c.prepareStatement(addUser)) {
            ps.setString(1, accountID);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, email);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add user failed.");
            status = false;
        }

        try (PreparedStatement ps = c.prepareStatement(addConfirmationCode)) {
            ps.setString(1, accountID);
            ps.setString(2, confirmationCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add code failed.");
            status = false;
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public boolean validateConfirmationCode(String account, String code) {

        // temp table userTest (for testing...)
        int count = 0;
        String query = "SELECT count(*) FROM passwordTest WHERE accountId = ? && confirmationCode = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, account);
            ps.setString(2, code);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Query failed.");
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count == 1;
    }

    public boolean setupPassword(String account, String password) {
        boolean status = true;
        String addConfirmationCode = "UPDATE passwordTest SET password = ? WHERE accountId = ?";

        try (PreparedStatement ps = c.prepareStatement(addConfirmationCode)) {
            ps.setString(1, password);
            ps.setString(2, account);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("set password failed.");
            status = false;
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return status;
    }

    public void addConfirmationCode(String email, String confirmationCode) {

        String addConfirmationCode = "UPDATE passwordTest SET confirmationCode = ? WHERE accountId IN (SELECT accountID from userTest WHERE email = ?)";

        try (PreparedStatement ps = c.prepareStatement(addConfirmationCode)) {
            ps.setString(2, email);
            ps.setString(1, confirmationCode);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("add user failed.");
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validateLogin(String account, String password) {
        int count = 0;
        String query = "SELECT count(*) FROM passwordTest WHERE accountId = ? && password = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, account);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException ex) {
            System.out.println("Query failed.");
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return count == 1;
    }





    public ArrayList<Station> getStations(){
        ArrayList<Station> stations = new ArrayList<>();
        String query = "SELECT * FROM TransportationSystem.Station";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stations.add(new Station(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5)));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Query failed.");
        }
        finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return stations;
    }



    public ArrayList<ScheduledRoute> getRoutesFFF(){
        String query = "SELECT * FROM TransportationSystem.Route_Driver_Vehicle";
        ArrayList<ScheduledRoute> scheduledRoutes = new ArrayList<>();
        try(PreparedStatement ps = c.prepareStatement(query)){
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    scheduledRoutes.add(new ScheduledRoute(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),rs.getTime(8),rs.getTime(9),rs.getTime(10),rs.getFloat(11),rs.getString(12),rs.getString(13)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return scheduledRoutes;

    }

}
