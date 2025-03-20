package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.Event;
import com.example.volunteerMatching.services.MatchService;
import com.example.volunteerMatching.services.EventService;
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

class MatchControllerTest {
    private MockMvc mockMvc;

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(matchController).build();
    }

    @Test
    void getAllEvents_shouldReturnEventList() throws Exception {
        Event event = new Event("Chicago", "Charity Run", "2025-04-20", List.of("Running"), "High", "Marathon for charity");
        
        // Fix: Properly mock the `MatchService` behavior
        when(matchService.matchVolunteers()).thenReturn(List.of("Charity Run -> John Doe"));

        mockMvc.perform(get("/match").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0]").value("Charity Run -> John Doe"));
    }

    @Test
    void matchVolunteer_shouldReturnBadRequestForInvalidRequest() throws Exception {
        mockMvc.perform(post("/match")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void matchVolunteer_shouldReturnSuccess() throws Exception {
        when(matchService.assignVolunteer("John Doe", "Charity Run")).thenReturn("John Doe assigned to Charity Run");

        mockMvc.perform(post("/match")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"volunteerName\":\"John Doe\", \"eventName\":\"Charity Run\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("John Doe assigned to Charity Run"));
    }

    @Test
    void testGetMatches_NoVolunteers_ShouldReturnEmptyList() throws Exception {
        when(matchService.matchVolunteers()).thenReturn(List.of());

        mockMvc.perform(get("/match"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
