package com.joysistvi.recordingapp.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    // Database Connection Parameters
    private final static String URL = "jdbc:mysql://localhost:3306/songs_db";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "";

    //Ducking Exception
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public boolean testConnection() {
        try (Connection conn = connect()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("DB Connection Failed: " + e.getMessage());
            return false;
        }
    }
}
