package com.example.volunteerMatching.models;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Event {
    @NotBlank(message = "Location is required.")
    private String location;
    @NotBlank(message = "Name is required.")
    @Size(max=100, message="Name must be 100 characters or less.")
    private String name;
    @NotBlank(message="Date is required.")
    private String date;
    @NotNull(message="Requirements are required.")
    private List<String> requirements;
    @NotBlank(message="Urgency is required.")
    @Pattern(regexp="High|Medium|Low", message="Urgency must be High, Medium, or Low.")
    private String urgency;
    @NotBlank(message="Description is required.")
    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", message="Invalid date format. Use YYYY-MM-DD")
    private String description;
    

    public Event(String location, String name, String date, List<String> requirements, String urgency, String description) {
        this.location = location;
        this.name = name;
        this.date = date;
        this.requirements = requirements;
        this.urgency = urgency;
        this.description = description;
    }

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public List<String> getRequirements() { return requirements; }
    public void setRequirements(List<String> requirements) { this.requirements = requirements; }

    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
