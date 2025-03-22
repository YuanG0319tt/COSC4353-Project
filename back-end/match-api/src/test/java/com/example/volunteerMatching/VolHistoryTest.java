package com.example.volunteerMatching.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

public class VolHistoryTest {
    
    @Test
    void testVolHistoryConstructor() {
        VolHistory volHistory = new VolHistory("Stuart", "me@mail.com", "8324597865", "Cleaning", "2025-09-25", 12, "Completed");

        assertEquals("Stuart", volHistory.getName());
        assertEquals("me@mail.com", volHistory.getEmail());
        assertEquals("8324597865", volHistory.getPhoneNumber());
        assertEquals("Cleaning", volHistory.getEventName());
        assertEquals("2025-09-25", volHistory.getEventDate());
        assertEquals(12, volHistory.getHoursVolunteered());
        assertEquals("Completed", volHistory.getStatus());
    }

    @Test
    public void testVolunteerSetters() {
        List<String> skillsResult = Arrays.asList("Lifting", "Coding");
        List<String> preferencesResult = Arrays.asList("Cleaning", "Food Pantry");
        Volunteer volunteer = new Volunteer("Houston", "Jasmine", "2025-03-27", skillsResult, preferencesResult);

        String newLocation = "Dallas";
        String newName = "Molly";
        String newAvailability = "2025-05-25";
        List<String> newSkills = Arrays.asList("Cooking", "Teaching");
        List<String> newPreferences = Arrays.asList("Outdoor", "Weekend");

        volunteer.setLocation(newLocation);
        volunteer.setName(newName);
        volunteer.setAvailability(newAvailability);
        volunteer.setSkills(newSkills);
        volunteer.setPreferences(newPreferences);

        assertEquals(newLocation, volunteer.getLocation());
        assertEquals(newName, volunteer.getName());
        assertEquals(newAvailability, volunteer.getAvailability());
        assertEquals(newSkills, volunteer.getSkills());
        assertEquals(newPreferences, volunteer.getPreferences());
    }
}
