package com.example.demo.service;

import com.example.demo.entity.UserProfile;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.entity.Volunteer;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private UserProfileMapper userProfileMapper;

    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    void getAllVolunteers_emptyRepository_returnsEmptyList() {
        when(userProfileMapper.selectList(null)).thenReturn(Collections.emptyList());

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        assertNotNull(volunteers);
        assertTrue(volunteers.isEmpty());
    }

    @Test
    void getAllVolunteers_nullCsvFields_returnsEmptyLists() {
        UserProfile profile = new UserProfile();
        profile.setFullName("Jordan Reyes");
        profile.setSkills(null);
        profile.setPreferences(null);
        profile.setCity("Houston");
        profile.setAvailability("Weekends");

        when(userProfileMapper.selectList(null)).thenReturn(List.of(profile));

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        assertEquals(1, volunteers.size());
        assertEquals(List.of(), volunteers.get(0).getSkills());
        assertEquals(List.of(), volunteers.get(0).getPreferences());
    }

    @Test
    void getAllVolunteers_csvParsing_splitsTrimsAndFilters() {
        UserProfile profile = new UserProfile();
        profile.setFullName("Anita Gomez");
        profile.setSkills("  First Aid , CPR , ,Crowd Management ");
        profile.setPreferences("  Emergency response, outdoor logistics  ");
        profile.setCity("Sugarland");
        profile.setAvailability("Evenings");

        when(userProfileMapper.selectList(null)).thenReturn(List.of(profile));

        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        assertEquals(1, volunteers.size());
        assertEquals(List.of("First Aid", "CPR", "Crowd Management"), volunteers.get(0).getSkills());
        assertEquals(List.of("Emergency response", "outdoor logistics"), volunteers.get(0).getPreferences());
    }
}
