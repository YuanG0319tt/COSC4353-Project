package com.example.volunteerMatching;

import com.example.volunteerMatching.services.VolunteerService;
import com.example.volunteerMatching.models.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerServiceTest {
    private VolunteerService volunteerService;

    @BeforeEach
    void setUp() {
        volunteerService = new VolunteerService();
    }

    @Test
    void testAddVolunteer() {
        Volunteer volunteer = new Volunteer("New York", "John Doe", "2025-06-10", List.of("First Aid"), List.of("Medical Help"));
        Volunteer addedVolunteer = volunteerService.addVolunteer(volunteer);

        assertEquals("John Doe", addedVolunteer.getName());
        assertEquals(1, volunteerService.getAllVolunteers().size());
    }

    @Test
    void testGetAllVolunteers() {
        Volunteer volunteer1 = new Volunteer("New York", "Alice", "2025-06-12", List.of("CPR"), List.of("Health Services"));
        Volunteer volunteer2 = new Volunteer("Los Angeles", "Bob", "2025-07-15", List.of("Teaching"), List.of("Education"));

        volunteerService.addVolunteer(volunteer1);
        volunteerService.addVolunteer(volunteer2);

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        assertEquals(2, volunteers.size());
        assertEquals("Alice", volunteers.get(0).getName());
        assertEquals("Bob", volunteers.get(1).getName());
    }

    @Test
    void testGetAllVolunteers_whenNoneExist() {
        assertTrue(volunteerService.getAllVolunteers().isEmpty());
    }
}
