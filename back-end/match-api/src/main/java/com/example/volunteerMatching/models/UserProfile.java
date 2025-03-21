package com.example.volunteerregistration.models;

import java.time.LocalDate;
import java.util.List;

public class UserProfile {
    private String fullName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private List<String> skills;     // Multiple skills
    private String preferences;      // Optional text area
    private List<LocalDate> availability; // Multiple date selection

    // Constructors
    public UserProfile() {
    }

    public UserProfile(String fullName, String address1, String address2,
                       String city, String state, String zip,
                       List<String> skills, String preferences,
                       List<LocalDate> availability) {
        this.fullName = fullName;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.skills = skills;
        this.preferences = preferences;
        this.availability = availability;
    }

    // Getters and setters
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }
    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }
    public List<String> getSkills() {
        return skills;
    }
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    public String getPreferences() {
        return preferences;
    }
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    public List<LocalDate> getAvailability() {
        return availability;
    }
    public void setAvailability(List<LocalDate> availability) {
        this.availability = availability;
    }
}
