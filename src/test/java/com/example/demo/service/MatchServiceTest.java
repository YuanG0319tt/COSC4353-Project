package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.repositories.EventDetailsRepository;
import com.example.demo.mapper.VolunteerHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private EventDetailsRepository eventRepo;

    @Mock
    private UserProfileMapper userProfileMapper;

    @Mock
    private VolunteerService volunteerService;

    @Mock
    private VolunteerHistoryMapper VolunteerHistoryMapper;

    @InjectMocks
    private MatchService matchService;

    private Volunteer v1, v2;
    private EventDetails e1, e2;

    @BeforeEach
    void setUp() {
        v1 = new Volunteer();
        v1.setName("Alice");
        v1.setSkills(List.of("Cooking"));
        v1.setPreferences(List.of("Driving"));
        v1.setLocation("Houston");
        v1.setAvailability(LocalDate.now().toString());

        v2 = new Volunteer();
        v2.setName("Bob");
        v2.setSkills(List.of("Driving"));
        v2.setPreferences(List.of("Cooking"));
        v2.setLocation("Austin");
        v2.setAvailability(null);

        e1 = new EventDetails();
        e1.setEventID(1);
        e1.setEventName("FoodDrive");
        e1.setRequiredSkills("Cooking");
        e1.setLocation("Houston");
        e1.setEventDate(Date.valueOf(LocalDate.now()));
        e1.setUrgency(2);

        e2 = new EventDetails();
        e2.setEventName("GardenHelp");
        e2.setRequiredSkills("Gardening");
        e2.setLocation("Austin");
        e2.setEventDate(java.sql.Date.valueOf(LocalDate.now().plusDays(1)));
        e2.setUrgency(1);
    }

    @Test
    void testFindBestEventsForVolunteerNotFound() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1));  // ✅ only needed stub

        var result = matchService.findBestEventsForVolunteer("NonExist");
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindBestEventsForVolunteer() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1, v2));
        when(eventRepo.findAll()).thenReturn(List.of(e1, e2));

        var list = matchService.findBestEventsForVolunteer("Alice");
        assertFalse(list.isEmpty());
        assertTrue(list.get(0).get("score").compareTo(list.get(1).get("score")) >= 0);
        assertEquals("Alice", list.get(0).get("volunteerName"));
    }

    @Test
    void testFindBestVolunteersForEventNotFound() {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of(v1));
        // when(eventRepo.findAll()).thenReturn(List.of(e1));

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
        when(VolunteerHistoryMapper.insertHistory(any())).thenReturn(1);
        UserProfile user = new UserProfile();
        user.setUid(10);
        user.setFullName("Alice");

        when(userProfileMapper.selectList(any())).thenReturn(List.of(user));
        when(eventRepo.findByEventNameIgnoreCase("FoodDrive")).thenReturn(Optional.of(e1));

        String message = matchService.assignVolunteer("Alice", "FoodDrive");
        assertEquals("Alice assigned to FoodDrive", message);

        ArgumentCaptor<VolunteerHistory> cap = ArgumentCaptor.forClass(VolunteerHistory.class);
        verify(VolunteerHistoryMapper).insertHistory(cap.capture());
        VolunteerHistory saved = cap.getValue();

        assertEquals(10, saved.getUserId());
        assertEquals(e1.getEventID(), saved.getEventId());
        assertEquals("Pending", saved.getStatus());
    }

    @Test
    void testAssignVolunteerFailure() {
        when(userProfileMapper.selectList(any())).thenReturn(List.of());

        String message = matchService.assignVolunteer("X", "Y");
        assertEquals("Assignment failed. Please check the names and try again.", message);
        verify(VolunteerHistoryMapper, never()).insert(any());
    }
}
