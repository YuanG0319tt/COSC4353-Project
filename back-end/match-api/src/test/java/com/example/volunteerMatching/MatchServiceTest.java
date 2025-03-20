package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Event;
import com.example.volunteerMatching.models.Volunteer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {
    private MatchService matchService;
    private VolunteerService volunteerService;
    private EventService eventService;

    @BeforeEach
    void setUp() {
        volunteerService = mock(VolunteerService.class);
        eventService = mock(EventService.class);
        matchService = new MatchService(volunteerService, eventService);
    }

    @Test
    void matchVolunteers_shouldReturnMatches() {
        Event event = new Event("Houston", "Cleaning Litter", "2025-03-07", List.of("Cleaning"), "Medium", "Cleaning litter on the streets of Houston.");
        Volunteer volunteer = new Volunteer("Houston", "Jasmine", "2025-063-07", List.of("Cleaning"), List.of("Cleaning"));

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));

        List<String> matches = matchService.matchVolunteers();
        assertEquals(1, matches.size());
        assertTrue(matches.get(0).contains("Jasmine"));
    }

    @Test
    void assignVolunteer_shouldFailWhenVolunteerNotFound() {
        Event event = new Event("Houston", "Food Drive", "2025-03-07", List.of("Packing"), "High", "Helping food distribution.");

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of());

        String result = matchService.assignVolunteer("Nonexistent Volunteer", "Food Drive");
        assertEquals("Assignment failed. Please try again.", result);
    }

    @Test
    void testMatchVolunteers_NoMatchingSkills() {
        Event event = new Event("Houston", "Cleanup", "2025-04-10", List.of("Medical Aid"), "High", "Helping in hospitals.");
        Volunteer volunteer = new Volunteer("Houston", "John Doe", "2025-04-10", List.of("Cooking"), List.of("Food Services"));

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));

        List<String> matches = matchService.matchVolunteers();
        
        assertTrue(matches.isEmpty()); // No match expected
    }
}
