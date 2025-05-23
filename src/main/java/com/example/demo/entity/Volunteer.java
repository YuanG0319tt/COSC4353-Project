package com.example.demo.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Size(min = 1, message = "At least one preference must be provided.")
    private List<String> preferences;

    public Volunteer(){
         
    };

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
