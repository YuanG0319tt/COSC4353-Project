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
}
