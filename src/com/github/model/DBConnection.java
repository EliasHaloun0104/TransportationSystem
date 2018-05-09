package com.github.model;

import com.github.controller.Booking;
import com.github.controller.Delays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
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
            user = prop.getProperty("userLoginProcess");
            password = prop.getProperty("passwordLoginProcess");
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

        String query = "SELECT count(*) FROM Account WHERE Username = ?";
        int count = dbValidation(query, username);

        return count > 0;
    }

    public boolean emailExists(String email) {

        String query = "SELECT count(*) FROM Account WHERE Email = ?";
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
        String query = "INSERT INTO Account (Username, FirstName, LastName, Email, PhoneNumber, ConfirmationCode) VALUES (?, ?, ? ,?, ?, ?)";
        String query2 = "INSERT INTO Balance (Account_Username) VALUES (?)";

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
        }

        if (status) {
            try (PreparedStatement ps = c.prepareStatement(query2)) {
                ps.setString(1, userName);
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("add balance failed.");
                status = false;
            } finally {
                try {
                    c.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    public void addConfirmationCode(String email, String confirmationCode) {
        String addConfirmationCode = "UPDATE Account SET ConfirmationCode = ? WHERE Email = ?";

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
        String query = "UPDATE Account SET Password = ? WHERE Username = ?";

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
        String query = "SELECT count(*) FROM Account WHERE Username = ? && ConfirmationCode = ?";

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

    public void makeBooking(int amount, String account_Username, int station_From, int station_TO, int route_Id){
        String query = "INSERT INTO Booking (AMOUNT, Account_Username, Station_From, Station_To, Route_Id) VALUE (?,?,?,?,?)";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1, amount);
            ps.setString(2, account_Username);
            ps.setInt(3, station_From);
            ps.setInt(4, station_TO);
            ps.setInt(5, route_Id);

            ps.executeUpdate();
            Account.getInstance().deductFromBalance(amount);
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

    public boolean validateLogin(String userName, String password) {
        int count = 0;
        String query = "SELECT count(*) FROM Account WHERE Username = ? && Password = ?";

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
    public void setBalance(int amount, String type, String userName){
        String query;
        if(type.equals("Deposit")){
            query = "INSERT into Balance (Deposit, Account_Username) VALUE (?,?)";
        }else{
            query = "INSERT into Balance (Payment, Account_Username) VALUE (?,?)";
        }

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1, amount);
            ps.setString(2, userName);
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

    public int getValue(String Username) {
        int total = 0;
        String query = "SELECT SUM(Deposit)-SUM(Payment) from Balance where Account_Username = ?";
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, Username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    total = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println("get value failed.");
            ex.printStackTrace();
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public GridPane getTransaction(){
        String query = "SELECT Deposit, Payment, CreationDate FROM Transportation_System_db.Balance WHERE Account_Username = ?";
        GridPane gridPane = new GridPane();
        int rowNo = 1;
        gridPane.add(new Label("Deposit"), 0,0);
        gridPane.add(new Label("Payment"), 1,0);
        gridPane.add(new Label("Time"), 2,0);
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, Account.getInstance().getAccountId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    gridPane.add(new Label(String.valueOf(rs.getInt(1))), 0,rowNo);
                    gridPane.add(new Label(String.valueOf(rs.getInt(2))), 1,rowNo);
                    gridPane.add(new Label(rs.getString(3)), 2,rowNo);
                    rowNo++;
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
        return gridPane;
    }

    public GridPane getBookingHistory(){
        String query = "SELECT Station_From,Station_To, AMOUNT, Booking.Date FROM Transportation_System_db.Booking WHERE Account_Username = ?";
        GridPane gridPane = new GridPane();
        int rowNo = 1;
        gridPane.add(new Label("From"), 0,0);
        gridPane.add(new Label("To"), 1,0);
        gridPane.add(new Label("Amount"), 3,0);
        gridPane.add(new Label("Date"), 4,0);
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, Account.getInstance().getAccountId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    gridPane.add(new Label(Destinations.getInstance().getStations().get(rs.getInt(1)).toString()), 0,rowNo);
                    gridPane.add(new Label(Destinations.getInstance().getStations().get(rs.getInt(2)).toString()), 1,rowNo);
                    gridPane.add(new Label(rs.getString(3)), 3,rowNo);
                    gridPane.add(new Label(rs.getString(4)), 4,rowNo);
                    rowNo++;
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
        return gridPane;
    }


    public String getRole(String userName) {
        String query = "Select ROLE from Account where Username = ?";
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
        String query = "Select Username, FirstName, LastName, Email, PhoneNumber, ROLE, CreationDate from Account where Username = ?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, userName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    for (int i = 1; i <= 7; i++) {
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

    public HashMap<Integer, Station> getStations() {
        HashMap<Integer, Station> stations = new HashMap<>();
        String query = "SELECT * FROM Transportation_System_db.Station";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stations.put(rs.getInt(1), new Station(rs));
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


    public ArrayList<String> getAvailableDestination(int from) {
        System.out.println(from);
        String query = "SELECT Station.Name FROM Station WHERE StationId IN (\n" +
                "SELECT AvailableDes FROM (\n" +
                "SELECT DISTINCT Schedule.Station_To AS AvailableDes FROM Schedule WHERE Route_Id IN (\n" +
                "SELECT DISTINCT Schedule.Route_Id FROM Schedule WHERE Station_From = ?) )AS Des WHERE AvailableDes != ?)";
        ArrayList<String> availableDestination = new ArrayList<>();
        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setInt(1,from);
            ps.setInt(2,from);
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


    public ScheduleOrganizer getRoutesFFF() {
        ScheduleOrganizer so = new ScheduleOrganizer();
        String query = "SELECT * FROM Transportation_System_db.Schedule";
        HashMap<Integer, ArrayList<ScheduledRoute>> scheduledRoutes = new HashMap<>();
        try (PreparedStatement ps = c.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    so.addToList(rs.getInt(5),new ScheduledRoute(rs));
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

        return so;
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

    public void addEmployee(String userName, String firstName, String lastName, String email,
                            String phoneNbr, String role, String password) {
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
            ps.setString(8, confirmationCode);


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

    public ObservableList<Booking> getBookings(String sql){
        ObservableList<Booking> bookings = FXCollections.observableArrayList();

        String query = sql;

        try (PreparedStatement ps = c.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                  bookings.add(new Booking(rs.getString(1),rs.getString(2),rs.getString(3),
                          rs.getString(4),rs.getString(5),rs.getString(6),
                          rs.getString(7)));

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

        return bookings;
    }
    public ObservableList<Delays> getSchedule(String sql) {
        ObservableList<Delays> delays = FXCollections.observableArrayList();

        String query = sql;

        try (PreparedStatement ps = c.prepareStatement(query)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    delays.add(new Delays(rs.getString(1), rs.getString(2), rs.getString(3)
                    ,rs.getString(4),rs.getString(5)));

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
        return delays;
    }
    public void updateDelay(String Delay, String DelayMessage) {
        String query = "Update Schedule set Delay = ?, DelayMessage = ? where ScheduleId =?";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, Delay);
            ps.setString(2, DelayMessage);

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
}
