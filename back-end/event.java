package com.example.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class EventManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventManagementApplication.class, args);
    }
}

@RestController
@RequestMapping("/events")
@CrossOrigin
class EventController {
    private final List<Event> events = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Event>> getEvents() {
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<?> addEvent(@Valid @RequestBody Event event) {
        events.add(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}

class Event {
    @NotBlank(message = "Name is required.")
    @Size(max = 100, message = "Name must be 100 characters or less.")
    private String name;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotBlank(message = "Location is required.")
    private String location;

    @NotNull(message = "Required skills are required.")
    private List<String> requiredSkills;

    @NotBlank(message = "Urgency is required.")
    @Pattern(regexp = "High|Medium|Low", message = "Urgency must be High, Medium, or Low.")
    private String urgency;

    @NotBlank(message = "Date is required.")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Use YYYY-MM-DD.")
    private String date;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

// Unit Tests
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EventValidationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testValidEvent() {
        Event event = new Event();
        event.setName("Team Meeting");
        event.setDescription("Monthly sync-up.");
        event.setLocation("Conference Room");
        event.setRequiredSkills(List.of("Communication"));
        event.setUrgency("High");
        event.setDate("2025-03-10");

        ResponseEntity<Event> response = restTemplate.postForEntity("/events", event, Event.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void testMissingFields() {
        Event event = new Event();
        event.setName("Incomplete Event");

        ResponseEntity<String> response = restTemplate.exchange("/events", HttpMethod.POST, new HttpEntity<>(event), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
