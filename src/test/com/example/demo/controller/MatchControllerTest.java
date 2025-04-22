package com.example.demo.controller;

import com.example.demo.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MatchController.class)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void getMatchesDelegatesToService() throws Exception {
        when(matchService.matchVolunteers())
                .thenReturn(List.of(Map.of("eventName","E1","volunteerName","V1")));

        mockMvc.perform(get("/match"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("E1"))
                .andExpect(jsonPath("$[0].volunteerName").value("V1"));

        verify(matchService).matchVolunteers();
    }

    @Test
    void assignVolunteerBadRequest() throws Exception {
        // missing both
        mockMvc.perform(post("/match")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid request data"));

        verify(matchService, never()).assignVolunteer(any(), any());
    }

    @Test
    void assignVolunteerOk() throws Exception {
        Map<String,String> body = Map.of(
                "volunteerName","Alice",
                "eventName","FoodDrive"
        );
        when(matchService.assignVolunteer("Alice","FoodDrive"))
                .thenReturn("done");

        mockMvc.perform(post("/match")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("done"));

        verify(matchService).assignVolunteer("Alice","FoodDrive");
    }

    @Test
    void getMatchesForVolunteer() throws Exception {
        when(matchService.findBestEventsForVolunteer("Alice"))
                .thenReturn(List.of(Map.of("volunteerName","Alice","eventName","E","score","5")));

        mockMvc.perform(get("/match/match/volunteer")
                        .param("name","Alice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("E"))
                .andExpect(jsonPath("$[0].volunteerName").value("Alice"));

        verify(matchService).findBestEventsForVolunteer("Alice");
    }

    @Test
    void getMatchesForEvent() throws Exception {
        when(matchService.findBestVolunteersForEvent("E"))
                .thenReturn(List.of(Map.of("volunteerName","V","eventName","E","score","3")));

        mockMvc.perform(get("/match/match/event")
                        .param("name","E"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].volunteerName").value("V"))
                .andExpect(jsonPath("$[0].eventName").value("E"));

        verify(matchService).findBestVolunteersForEvent("E");
    }

    @Test
    void topEventsDelegates() throws Exception {
        when(matchService.findBestEventsForVolunteer("Bob"))
                .thenReturn(List.of(Map.of("eventName","X")));

        mockMvc.perform(get("/match/top-events")
                        .param("volunteer","Bob"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("X"));

        verify(matchService).findBestEventsForVolunteer("Bob");
    }

    @Test
    void topVolunteersDelegates() throws Exception {
        when(matchService.findBestVolunteersForEvent("X"))
                .thenReturn(List.of(Map.of("volunteerName","V")));

        mockMvc.perform(get("/match/top-volunteers")
                        .param("event","X"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].volunteerName").value("V"));

        verify(matchService).findBestVolunteersForEvent("X");
    }
}
