package com.github.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    public DBConnection() {

        // load properties file
        Properties prop = new Properties();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("resources/db.properties")) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ssl certificate config
        System.setProperty("javax.net.ssl.trustStore", System.getProperty("user.home")
                + System.getProperty("file.separator")
                + "myKeyStore");
        System.setProperty("javax.net.ssl.trustStorePassword", prop.getProperty("trustStorePassword"));

        // db credentials
        String url = prop.getProperty("database");
        String user = prop.getProperty("user");
        String password = prop.getProperty("password");

        // db connection
        try (Connection c = DriverManager.getConnection(url, user, password)) {
            System.out.println("It's working!");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
