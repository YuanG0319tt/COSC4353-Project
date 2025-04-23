package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.Volunteer;
import com.example.demo.repositories.UserInfoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class VolunteerServiceTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @InjectMocks
    private VolunteerService service;

    @Test
    void getAllVolunteers_emptyRepository_returnsEmptyList() {
        when(userInfoRepository.findAll()).thenReturn(Collections.emptyList());

        List<Volunteer> result = service.getAllVolunteers();

        assertNotNull(result);
        assertTrue(result.isEmpty(), "Expected no volunteers when repository is empty");
    }

    @Test
    void getAllVolunteers_nullCsvFields_returnsEmptyLists() {
        UserInfo ui = new UserInfo();
        ui.setName("John");
        ui.setCity("CityX");
        ui.setAvailability("2025-04-22");
        ui.setSkills(null);
        ui.setPreferences(null);

        when(userInfoRepository.findAll()).thenReturn(List.of(ui));

        List<Volunteer> volunteers = service.getAllVolunteers();
        assertEquals(1, volunteers.size());

        Volunteer v = volunteers.get(0);
        assertEquals("John", v.getName());
        assertEquals("CityX", v.getLocation());
        assertEquals("2025-04-22", v.getAvailability());
        assertTrue(v.getSkills().isEmpty(),       "Null skills → empty list");
        assertTrue(v.getPreferences().isEmpty(),  "Null preferences → empty list");
    }

    @Test
    void getAllVolunteers_csvParsing_splitsTrimsAndFilters() {
        UserInfo ui = new UserInfo();
        ui.setName("Jane");
        ui.setCity("CityY");
        ui.setAvailability("2025-05-01");
        // note the empty segment between commas
        ui.setSkills("a, b, ,c");
        ui.setPreferences(" x ,y");

        when(userInfoRepository.findAll()).thenReturn(List.of(ui));

        List<Volunteer> volunteers = service.getAllVolunteers();
        Volunteer v = volunteers.get(0);

        assertEquals(List.of("a", "b", "c"), v.getSkills(), "Skills should be split, trimmed, and empty entries dropped");
        assertEquals(List.of("x", "y"),     v.getPreferences(), "Preferences should be split and trimmed");
    }

    @Test
    void addVolunteer_mapsFieldsAndSavesUserInfo() {
        Volunteer input = new Volunteer(
                "LocZ",
                "Dana",
                "2025-06-01",
                List.of("S1","S2"),
                List.of("P1")
        );

        // capture the UserInfo passed to the repository
        ArgumentCaptor<UserInfo> captor = ArgumentCaptor.forClass(UserInfo.class);
        when(userInfoRepository.save(any(UserInfo.class)))
                .thenAnswer(inv -> inv.getArgument(0));  // echo back

        Volunteer returned = service.addVolunteer(input);

        // should return the same Volunteer instance
        assertSame(input, returned);

        // verify that we saved a UserInfo with the correct mappings
        verify(userInfoRepository).save(captor.capture());
        UserInfo saved = captor.getValue();
        assertEquals("Dana",           saved.getName());
        assertEquals("S1,S2",          saved.getSkills());
        assertEquals("P1",             saved.getPreferences());
        assertEquals("LocZ",           saved.getCity());
        assertEquals("2025-06-01",     saved.getAvailability());
    }
}
