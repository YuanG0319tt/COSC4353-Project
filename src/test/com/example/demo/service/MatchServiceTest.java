package com.example.demo.service;

import com.example.demo.entity.EventDetails;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.Volunteer;
import com.example.demo.entity.VolunteerHistoryJ;
import com.example.demo.repositories.EventDetailsRepository;
import com.example.demo.repositories.UserInfoRepository;
import com.example.demo.repositories.VolunteerHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private EventDetailsRepository eventRepo;
    @Mock
    private UserInfoRepository userRepo;
    @Mock
    private VolunteerService volunteerService;
    @Mock
    private VolunteerHistoryRepository volunteerHistoryRepository;

    @InjectMocks
    private MatchService matchService;

    private Volunteer v1, v2;
    private EventDetails e1, e2;

    @BeforeEach
    void setUp() {
        // volunteer #1: skill = A, pref = B, location L, availability today
        v1 = new Volunteer();
        v1.setName("Alice");
        v1.setSkills(List.of("Cooking"));
        v1.setPreferences(List.of("Driving"));
        v1.setLocation("Houston");
        v1.setAvailability(LocalDate.now().toString());

        // volunteer #2: skill = B, pref = A, wrong loc, no availability
        v2 = new Volunteer();
        v2.setName("Bob");
        v2.setSkills(List.of("Driving"));
        v2.setPreferences(List.of("Cooking"));
        v2.setLocation("Austin");
        v2.setAvailability(null);

        // event #1 requires Cooking, location Houston, urgency 2, today
        e1 = new EventDetails();
        e1.setEventName("FoodDrive");
        e1.setRequiredSkills("Cooking");
        e1.setLocation("Houston");
        e1.setEventDate(LocalDate.now());
        e1.setUrgency(2);

        // event #2 requires Gardening, location Austin, urgency 1, tomorrow
        e2 = new EventDetails();
        e2.setEventName("GardenHelp");
        e2.setRequiredSkills("Gardening");
        e2.setLocation("Austin");
        e2.setEventDate(LocalDate.now().plusDays(1));
        e2.setUrgency(1);
    }




    @Test
    void testFindBestEventsForVolunteerNotFound() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1));
        when(eventRepo.findAll()).thenReturn(List.of(e1));

        var result = matchService.findBestEventsForVolunteer("NonExist");
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBestEventsForVolunteer() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1, v2));
        when(eventRepo.findAll()).thenReturn(List.of(e1, e2));

        var list = matchService.findBestEventsForVolunteer("Alice");
        // should return up to 5 sorted by score descending
        assertFalse(list.isEmpty());
        assertTrue(list.get(0).get("score").compareTo(list.get(1).get("score")) >= 0);
        assertEquals("Alice", list.get(0).get("volunteerName"));
    }

    @Test
    void testFindBestVolunteersForEventNotFound() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1));
        when(eventRepo.findAll()).thenReturn(List.of(e1));

        var result = matchService.findBestVolunteersForEvent("NoEvent");
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBestVolunteersForEvent() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1, v2));
        when(eventRepo.findAll()).thenReturn(List.of(e1, e2));

        var list = matchService.findBestVolunteersForEvent("FoodDrive");
        assertFalse(list.isEmpty());
        assertEquals("FoodDrive", list.get(0).get("eventName"));
        assertTrue(list.get(0).containsKey("score"));
    }

    @Test
    void testAssignVolunteerSuccess() {
        UserInfo user = new UserInfo();
        user.setUserId(10L);
        user.setName("Alice");
        when(userRepo.findAll()).thenReturn(List.of(user));
        when(eventRepo.findAll()).thenReturn(List.of(e1));

        String message = matchService.assignVolunteer("Alice", "FoodDrive");
        assertEquals("Alice assigned to FoodDrive", message);
        // should save a history record
        ArgumentCaptor<VolunteerHistoryJ> cap = ArgumentCaptor.forClass(VolunteerHistoryJ.class);
        verify(volunteerHistoryRepository).save(cap.capture());
        VolunteerHistoryJ saved = cap.getValue();
        assertEquals(10,   saved.getUid());
        assertEquals( e1.getEventID(), saved.getEventId());
        assertEquals("Pending", saved.getParticipationStatus());
    }

    @Test
    void testAssignVolunteerFailure() {
        when(userRepo.findAll()).thenReturn(List.of());
        when(eventRepo.findAll()).thenReturn(List.of());
        String message = matchService.assignVolunteer("X", "Y");
        assertEquals("Assignment failed. Please try again.", message);
        verify(volunteerHistoryRepository, never()).save(any());
    }
}
