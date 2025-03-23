package com.example.volunteerMatching;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.services.MatchService;
import com.example.volunteerMatching.services.VolunteerService;
import com.example.volunteerMatching.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
        EventDetails event = new EventDetails();
        event.setLocation("Houston");
        event.setEventName("Cleaning Litter");
        event.setEventDate(LocalDate.of(2025, 3, 7));
        event.setRequiredSkills("Cleaning");
        event.setUrgency(3);
        event.setDescription("Cleaning litter on the streets of Houston.");

        Volunteer volunteer = new Volunteer();
        volunteer.setLocation("Houston");
        volunteer.setName("Jasmine");
        volunteer.setAvailability("2025-03-07");
        volunteer.setSkills(List.of("Cleaning"));
        volunteer.setPreferences(List.of("Cleaning"));

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));

        List<String> matches = matchService.matchVolunteers();

        assertEquals(1, matches.size());
        assertTrue(matches.get(0).contains("Jasmine"));
    }

    @Test
    void assignVolunteer_shouldFailWhenVolunteerNotFound() {
        EventDetails event = new EventDetails();
        event.setLocation("Houston");
        event.setEventName("Food Drive");
        event.setEventDate(LocalDate.of(2025, 3, 7));
        event.setRequiredSkills("Packing");
        event.setUrgency(5);
        event.setDescription("Helping food distribution.");

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of());

        String result = matchService.assignVolunteer("Nonexistent Volunteer", "Food Drive");
        assertEquals("Assignment failed. Please try again.", result);
    }

    @Test
    void testMatchVolunteers_NoMatchingSkills() {
        EventDetails event = new EventDetails();
        event.setLocation("Houston");
        event.setEventName("Cleanup");
        event.setEventDate(LocalDate.of(2025, 4, 10));
        event.setRequiredSkills("Medical Aid");
        event.setUrgency(5);
        event.setDescription("Helping in hospitals.");

        Volunteer volunteer = new Volunteer();
        volunteer.setLocation("Houston");
        volunteer.setName("John Doe");
        volunteer.setAvailability("2025-04-10");
        volunteer.setSkills(List.of("Cooking"));
        volunteer.setPreferences(List.of("Food Services"));

        when(eventService.getAllEvents()).thenReturn(List.of(event));
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));

        List<String> matches = matchService.matchVolunteers();
        assertTrue(matches.isEmpty());
    }
}
