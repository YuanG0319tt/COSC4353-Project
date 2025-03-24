package com.example.volunteerMatching.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
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

            // Check if VolunteerHistory table exists
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW TABLES LIKE 'VolunteerHistory';");
            if (rs.next()) {
                System.out.println("Table VolunteerHistory exists.");
            } else {
                System.out.println("Table VolunteerHistory does NOT exist!");
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to AWS MySQL");
            e.printStackTrace();
        }
    }
}