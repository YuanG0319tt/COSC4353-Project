package com.example.volunteerMatching.models;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class VolunteerTest {
    
    @Test
    void testVolunteerConstructor() {
        List<String> skillsResult = Arrays.asList("Lifting", "Coding");
        List<String> preferencesResult = Arrays.asList("Cleaning", "Food Pantry");
        Volunteer volunteer = new Volunteer("Houston", "Jasmine", "2025-03-27", skillsResult, preferencesResult);

        assertEquals("Houston", volunteer.getLocation());
        assertEquals("Jasmine", volunteer.getName());
        assertEquals("2025-03-27", volunteer.getAvailability());
        assertIterableEquals(skillsResult, volunteer.getSkills());
        assertIterableEquals(preferencesResult, volunteer.getPreferences());
    }
}