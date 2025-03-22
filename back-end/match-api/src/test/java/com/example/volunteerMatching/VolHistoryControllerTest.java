package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.VolHistory;
import com.example.volunteerMatching.services.VolHistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolHistoryController.class)
public class VolHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolHistoryService volHistoryService;

    @Test
    void testAddVolunteerHistory() throws Exception {
        VolHistory history = new VolHistory(
                "John Doe",
                "test@example.com",
                "1234567890",
                "Food Drive",
                "2025-04-01",
                5,
                "Completed"
        );

        Mockito.when(volHistoryService.addVolHistory(any(VolHistory.class))).thenReturn(history);

        String json = """
            {
                "name": "John Doe",
                "email": "test@example.com",
                "phoneNumber": "1234567890",
                "eventName": "Food Drive",
                "eventDate": "2025-04-01",
                "hoursVolunteered": 5,
                "status": "Completed"
            }
        """;

        mockMvc.perform(post("/volunteer-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetAllVolunteerHistory() throws Exception {
        VolHistory history = new VolHistory(
                "John Doe",
                "test@example.com",
                "1234567890",
                "Food Drive",
                "2025-04-01",
                5,
                "Completed"
        );

        Mockito.when(volHistoryService.getAllVolHistory()).thenReturn(List.of(history));

        mockMvc.perform(get("/volunteer-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("test@example.com")));
    }

    @Test
    void testGetVolunteerHistoryById_Found() throws Exception {
        VolHistory history = new VolHistory(
                "John Doe",
                "test@example.com",
                "1234567890",
                "Food Drive",
                "2025-04-01",
                5,
                "Completed"
        );

        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.of(history));

        mockMvc.perform(get("/volunteer-history/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void testGetVolunteerHistoryById_NotFound() throws Exception {
        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/volunteer-history/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteVolunteerHistory_Found() throws Exception {
        VolHistory history = new VolHistory(
                "John Doe",
                "test@example.com",
                "1234567890",
                "Food Drive",
                "2025-04-01",
                5,
                "Completed"
        );

        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.of(history));

        mockMvc.perform(delete("/volunteer-history/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteVolunteerHistory_NotFound() throws Exception {
        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/volunteer-history/1"))
                .andExpect(status().isNotFound());
    }
}