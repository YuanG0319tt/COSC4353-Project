package com.example.volunteerMatching.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://nabadatabase.cliacqmekc7s.us-east-2.rds.amazonaws.com:3306/nabaDatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "nabaTest76!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connected to AWS MySQL successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
