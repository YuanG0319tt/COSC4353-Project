package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "volunteer_history")
public class VolHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    private String phoneNumber;

    @NotBlank(message = "Event name is required.")
    private String eventName;

    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format.")
    private String eventDate;

    @Min(value = 1, message = "Hours must be at least 1.")
    private int hoursVolunteered;

    @Pattern(regexp = "Completed|Pending", message = "Status must be 'Completed' or 'Pending'.")
    private String status;  // Completed or Pending

    public VolHistory(String name, String email, String phoneNumber, String eventName, String eventDate, int hoursVolunteered, String status) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.hoursVolunteered = hoursVolunteered;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public int getHoursVolunteered() { return hoursVolunteered; }
    public void setHoursVolunteered(int hoursVolunteered) { this.hoursVolunteered = hoursVolunteered; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
