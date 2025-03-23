package com.example.volunteerMatching;

import com.example.volunteerMatching.models.Volunteer;
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
