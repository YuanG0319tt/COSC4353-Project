package com.example.volunteerMatching.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class UserProfileTest {

    @Test
    void testUserProfileConstructor() {
        List<String> skillsResult = Arrays.asList("Lifting", "Organizing");

        // Define start and end dates for availability
        LocalDate start = LocalDate.of(2025, 5, 19);
        LocalDate end = LocalDate.of(2025, 5, 20);

        // Generate availability list
        List<LocalDate> availabilityResult = start.datesUntil(end.plusDays(1), Period.ofDays(1)).toList();

        // Create the profile
        UserProfile profile = new UserProfile("John Smith", "123 Easy Street", "Apt. 123", "Houston", "Texas", "77029", skillsResult, "Cleaning", availabilityResult);

        // Assertions
        assertEquals("John Smith", profile.getFullName());
        assertEquals("123 Easy Street", profile.getAddress1());
        assertEquals("Apt. 123", profile.getAddress2());
        assertEquals("Houston", profile.getCity());
        assertEquals("Texas", profile.getState());
        assertEquals("77029", profile.getZip());
        assertIterableEquals(skillsResult, profile.getSkills());
        assertEquals("Cleaning", profile.getPreferences());
        assertIterableEquals(availabilityResult, profile.getAvailability());
    }

    @Test
    public void testUserProfileSetters() {
        List<String> skillsResult = Arrays.asList("Lifting", "Organizing");
        List<String> skillsSetter = Arrays.asList("Sweeping", "Coding");

        LocalDate start = LocalDate.of(2025, 5, 19);
        LocalDate end = LocalDate.of(2025, 5, 20);
        List<LocalDate> availabilityResult = start.datesUntil(end.plusDays(1), Period.ofDays(1)).toList();
        LocalDate startSetter = LocalDate.of(2025, 5, 10);
        LocalDate endSetter = LocalDate.of(2025, 5, 19);
        List<LocalDate> availabilitySetter = startSetter.datesUntil(endSetter.plusDays(1), Period.ofDays(1)).toList();

        UserProfile profile = new UserProfile("John Smith", "123 Easy Street", "Apt. 123", "Houston", "Texas", "77029", skillsResult, "Cleaning", availabilityResult);

        String newName = "Jane Smith";
        String newAddress1 = "321 Hard Lane";
        String newAddress2 = "Apt. 321";
        String newCity = "New Orleans";
        String newState = "Louisiana";
        String newZip = "12378";
        List<String> newSkills = skillsSetter;
        String newPreferences = "Sorting";
        List<LocalDate> newAvailability = availabilitySetter;

        profile.setFullName(newName);
        profile.setAddress1(newAddress1);
        profile.setAddress2(newAddress2);
        profile.setCity(newCity);
        profile.setState(newState);
        profile.setZip(newZip);
        profile.setSkills(newSkills);
        profile.setPreferences(newPreferences);
        profile.setAvailability(newAvailability);

        assertEquals(newName, profile.getFullName());
        assertEquals(newAddress1, profile.getAddress1());
        assertEquals(newAddress2, profile.getAddress2());
        assertEquals(newCity, profile.getCity());
        assertEquals(newState, profile.getState());
        assertEquals(newZip, profile.getZip());
        assertIterableEquals(newSkills, profile.getSkills());
        assertEquals(newPreferences, profile.getPreferences());
        assertIterableEquals(newAvailability, profile.getAvailability());
    }
}
