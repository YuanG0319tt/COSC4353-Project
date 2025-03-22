package com.example.volunteerMatching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.volunteerMatching.config.DatabaseConnection;
import com.example.volunteerMatching.config.CorsConfig;
import com.example.volunteerMatching.config.DotenvConfig;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Running the application");
        try {
            CorsConfig.corsConfigurer();
            System.out.println("CORS configured!");
        } catch (Exception e) {
            System.out.println("Error connecting to AWS MySQL");
            e.printStackTrace();
        }
        try {
            DotenvConfig.loadEnvVariables();
            System.out.println("Environment variables loaded!");
        } catch (Exception e) {
            System.out.println("Error loading environment variables");
            e.printStackTrace();
        }
        try {
            DatabaseConnection.main(new String[]{});
        } catch (Exception e) {
            System.out.println("Database connection test failed.");
            e.printStackTrace();
        }
    }
}
