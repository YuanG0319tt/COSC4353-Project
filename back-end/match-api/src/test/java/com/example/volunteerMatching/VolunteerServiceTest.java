package com.example.volunteerMatching;

import com.example.volunteerMatching.models.UserInfo;
import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.repositories.UserInfoRepository;
import com.example.volunteerMatching.services.VolunteerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VolunteerServiceTest {
    private VolunteerService volunteerService;
    private UserInfoRepository userInfoRepository;

    @BeforeEach
    void setUp() {
        userInfoRepository = mock(UserInfoRepository.class);
        volunteerService = new VolunteerService(userInfoRepository);
    }

    @Test
    void testAddVolunteer() {
        Volunteer volunteer = new Volunteer("New York", "John Doe", "2025-06-10", List.of("First Aid"), List.of("Medical Help"));

        // simulate saving
        when(userInfoRepository.save(any(UserInfo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Volunteer addedVolunteer = volunteerService.addVolunteer(volunteer);
        assertEquals("John Doe", addedVolunteer.getName());
    }

    @Test
    void testGetAllVolunteers_whenNoneExist() {
        when(userInfoRepository.findAll()).thenReturn(List.of());
        assertTrue(volunteerService.getAllVolunteers().isEmpty());
    }

    @Test
    void testGetAllVolunteers_withData() {
        UserInfo u1 = new UserInfo();
        u1.setName("Alice");
        u1.setCity("New York");
        u1.setAvailability("2025-06-12");
        u1.setSkills("CPR");
        u1.setPreferences("Health Services");

        UserInfo u2 = new UserInfo();
        u2.setName("Bob");
        u2.setCity("Los Angeles");
        u2.setAvailability("2025-07-15");
        u2.setSkills("Teaching");
        u2.setPreferences("Education");

        when(userInfoRepository.findAll()).thenReturn(List.of(u1, u2));

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        assertEquals(2, volunteers.size());
        assertEquals("Alice", volunteers.get(0).getName());
        assertEquals("Bob", volunteers.get(1).getName());
    }
}
