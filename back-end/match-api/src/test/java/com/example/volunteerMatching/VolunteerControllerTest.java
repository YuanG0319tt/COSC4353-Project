package com.example.volunteerMatching;

import com.example.volunteerMatching.controllers.VolunteerController;
import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.services.VolunteerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VolunteerControllerTest {
    private MockMvc mockMvc;

    @Mock
    private VolunteerService volunteerService;

    @InjectMocks
    private VolunteerController volunteerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(volunteerController).build();
    }

    @Test
    void testAddVolunteer() throws Exception {
        Volunteer volunteer = new Volunteer("Seattle", "Jane Doe", "2025-09-10", List.of("Cooking"), List.of("Food Services"));
        when(volunteerService.addVolunteer(any(Volunteer.class))).thenReturn(volunteer);

        mockMvc.perform(post("/volunteers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"location\":\"Seattle\", \"name\":\"Jane Doe\", \"availability\":\"2025-09-10\", \"skills\":[\"Cooking\"], \"preferences\":[\"Food Services\"]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testGetAllVolunteers() throws Exception {
        Volunteer volunteer1 = new Volunteer("Chicago", "Tom", "2025-10-15", List.of("First Aid"), List.of("Medical Help"));
        Volunteer volunteer2 = new Volunteer("Houston", "Anna", "2025-11-20", List.of("Driving"), List.of("Transport Services"));

        when(volunteerService.getAllVolunteers()).thenReturn(List.of(volunteer1, volunteer2));

        mockMvc.perform(get("/volunteers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[1].name").value("Anna"));
    }

    @Test
    void testGetAllVolunteers_whenNoneExist() throws Exception {
        when(volunteerService.getAllVolunteers()).thenReturn(List.of());

        mockMvc.perform(get("/volunteers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
