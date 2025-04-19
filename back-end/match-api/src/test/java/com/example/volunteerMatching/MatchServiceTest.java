package com.example.volunteerMatching;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.services.MatchService;
import com.example.volunteerMatching.services.VolunteerService;
import com.example.volunteerMatching.repositories.EventDetailsRepository;
import com.example.volunteerMatching.repositories.UserInfoRepository;
import com.example.volunteerMatching.repositories.VolunteerHistoryRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    private MatchService matchService;

    private VolunteerService volunteerService;
    private EventDetailsRepository eventRepo;
    private UserInfoRepository userRepo;
    private VolunteerHistoryRepository volunteerHistoryRepo;

    @BeforeEach
    void setUp() {
        volunteerService = mock(VolunteerService.class);
        eventRepo = mock(EventDetailsRepository.class);
        userRepo = mock(UserInfoRepository.class);
        volunteerHistoryRepo = mock(VolunteerHistoryRepository.class);

        matchService = new MatchService(eventRepo, userRepo, volunteerService, volunteerHistoryRepo);
    }

    @Test
    void findBestEventsForVolunteer_shouldReturnMatches() {
        Volunteer volunteer = new Volunteer("Houston", "Jane", "2025-06-01", List.of("Cooking"), List.of("Food"));
        EventDetails event = new EventDetails();
        event.setEventName("Food Drive");
        event.setEventDate(LocalDate.of(2025, 6, 1));
        event.setRequiredSkills("Cooking");
        event.setLocation("Houston");
        event.setUrgency(3);
        event.setDescription("Helping with food packaging");

        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer));
        when(eventRepo.findAll()).thenReturn(List.of(event));

        List<Map<String, String>> matches = matchService.findBestEventsForVolunteer("Jane");

        assertEquals(1, matches.size());
        assertEquals("Food Drive", matches.get(0).get("eventName"));
        assertEquals("Jane", matches.get(0).get("volunteerName"));
    }
}
