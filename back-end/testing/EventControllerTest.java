package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.Event;
import com.example.volunteerMatching.services.EventService;
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

class EventControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void testAddEvent() throws Exception {
        Event event = new Event("NYC", "Cleanup Drive", "2025-06-20", List.of("Gloves", "Trash Bags"), "High", "2025-06-20");
        when(eventService.addEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"location\":\"NYC\", \"name\":\"Cleanup Drive\", \"date\":\"2025-06-20\", \"requirements\":[\"Gloves\", \"Trash Bags\"], \"urgency\":\"High\", \"description\":\"2025-06-20\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Cleanup Drive"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        Event event1 = new Event("LA", "Tree Plantation", "2025-07-10", List.of("Shovel", "Saplings"), "Medium", "2025-07-10");
        Event event2 = new Event("SF", "Beach Cleanup", "2025-08-05", List.of("Gloves", "Bags"), "Low", "2025-08-05");

        when(eventService.getAllEvents()).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Tree Plantation"))
                .andExpect(jsonPath("$[1].name").value("Beach Cleanup"));
    }
}