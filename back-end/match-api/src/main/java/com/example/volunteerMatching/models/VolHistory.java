package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "VolunteerHistory") 
@NoArgsConstructor
@Getter
@Setter
public class VolHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required.")
    @Column(name = "name")
    private String name;
    
    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    @Column(name = "email")
    private String email;
    
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits.")
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @NotBlank(message = "Event name is required.")
    @Column(name = "event_name")
    private String eventName;
    
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format.")
    @Column(name = "event_date")
    private String eventDate;
    
    @Min(value = 1, message = "Hours must be at least 1.")
    @Column(name = "hours_volunteered")
    private int hoursVolunteered;
    
    @Pattern(regexp = "Completed|Pending", message = "Status must be 'Completed' or 'Pending'.")
    @Column(name = "status")
    private String status;
    
    // Constructor
    public VolHistory(String name, String email, String phoneNumber, String eventName, String eventDate, int hoursVolunteered, String status) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.hoursVolunteered = hoursVolunteered;
        this.status = status;
    }
    
    // Getters and setters already included via Lombok
}