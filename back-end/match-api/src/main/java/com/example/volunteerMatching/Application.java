package com.example.volunteerMatching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.util.List;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.repositories.EventDetailsRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

@SpringBootApplication
@EntityScan(basePackages = "com.example.volunteerMatching.models")
@EnableJpaRepositories(basePackages = "com.example.volunteerMatching.repositories")
public class Application {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner showDbConnectionInfo() {
        return args -> {
            System.out.println("DB URL from Spring: " + env.getProperty("spring.datasource.url"));
            System.out.println("DB User from Spring: " + env.getProperty("spring.datasource.username"));
        };
    }

    @Bean
    CommandLineRunner testEventDetailsRepo(EventDetailsRepository repo) {
        return args -> {
            List<EventDetails> allEvents = repo.findAll();
            System.out.println("=== Repository Test ===");
            System.out.println("Found " + allEvents.size() + " events from DB");
            allEvents.forEach(System.out::println);
        };
    }

    @Bean
    CommandLineRunner testEvents(EventDetailsRepository repository) {
        return args -> {
            List<EventDetails> events = repository.findAll();
            System.out.println("=== Repository Test ===");
            System.out.println("Found " + events.size() + " events from DB");
            for (EventDetails e : events) {
                System.out.println(" -> " + e.getEventName());
            }
        };
    }
        
    @Bean
    CommandLineRunner testDirectSQL(DataSource dataSource) {
        return args -> {
            try (Connection conn = dataSource.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM EventDetails")) {

                if (rs.next()) {
                    System.out.println("✔ Rows in EventDetails: " + rs.getInt(1));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("✖ Failed to execute raw SQL test.");
            }
        };
    }
}