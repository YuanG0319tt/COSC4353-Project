package com.example.volunteerMatching.models;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Volunteer {
    @NotBlank(message="Location is required.")
    private String location;
    @NotBlank(message="Name is required.")
    @Size(max=100, message="Name must be 100 characters or less.")
    private String name;
    @NotBlank(message="Availability is required.")
    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", message="Invalid date format. Use YYYY-MM-DD")
    private String availability;
    @NotNull(message="Skills are required.")
    private List<String> skills;
    @NotBlank(message="Preferences are required.")
    private List<String> preferences;

    public Volunteer(String location, String name, String availability, List<String> skills, List<String> preferences) {
        this.location = location;
        this.name = name;
        this.availability = availability;
        this.skills = skills;
        this.preferences = preferences;
    }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<String> getPreferences() { return preferences; }
    public void setPreferences(List<String> preferences) { this.preferences = preferences; }
}
