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

    VolHistory mockHistory = new VolHistory(
        "Stuart",
        "test@example.com",
        "8324597865",
        "Food Drive",
        "2025-04-01",
        5,
        "Completed"
    );

    String jsonBody = """
        {
            "name": "Stuart",
            "email": "test@example.com",
            "phoneNumber": "8324597865",
            "eventName": "Food Drive",
            "eventDate": "2025-04-01",
            "hoursVolunteered": 5,
            "status": "Completed"
        }
    """;

    @Test
    void testAddVolunteerHistory() throws Exception {
        Mockito.when(volHistoryService.addVolHistory(any(VolHistory.class))).thenReturn(mockHistory);

        mockMvc.perform(post("/volunteer-history")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.eventName").value("Food Drive"));
    }

    @Test
    void testGetAllVolunteerHistory() throws Exception {
        Mockito.when(volHistoryService.getAllVolHistory()).thenReturn(List.of(mockHistory));

        mockMvc.perform(get("/volunteer-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is("test@example.com")));
    }

    @Test
    void testGetVolunteerHistoryById_Found() throws Exception {
        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.of(mockHistory));

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
        Mockito.when(volHistoryService.getVolHistoryById(1L)).thenReturn(Optional.of(mockHistory));

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
