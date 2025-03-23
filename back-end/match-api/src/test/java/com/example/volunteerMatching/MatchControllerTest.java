package com.example.volunteerMatching;

import com.example.volunteerMatching.controllers.MatchController;
import com.example.volunteerMatching.services.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

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
        // Mock matchService response
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
                .andExpect(jsonPath("$.message").value("John Doe assigned to Charity Run")); // ✅ FIXED LINE
    }

    @Test
    void testGetMatches_NoVolunteers_ShouldReturnEmptyList() throws Exception {
        when(matchService.matchVolunteers()).thenReturn(List.of());

        mockMvc.perform(get("/match"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
