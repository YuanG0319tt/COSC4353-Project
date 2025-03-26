package com.example.volunteerMatching;

import com.example.volunteerMatching.controllers.EventController;
import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
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
        EventDetails event = new EventDetails();
        event.setLocation("NYC");
        event.setEventName("Cleanup Drive");
        event.setEventDate(LocalDate.parse("2025-06-20"));
        event.setRequiredSkills("Gloves,Trash Bags");
        event.setUrgency(5);
        event.setDescription("City-wide cleanup event");

        when(eventService.addEvent(any(EventDetails.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"location\":\"NYC\", \"eventName\":\"Cleanup Drive\", \"eventDate\":\"2025-06-20\", \"requirements\":\"Gloves,Trash Bags\", \"urgency\":5, \"description\":\"City-wide cleanup event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("Cleanup Drive"));
    }

    @Test
    void testGetAllEvents() throws Exception {
        EventDetails event1 = new EventDetails();
        event1.setLocation("LA");
        event1.setEventName("Tree Plantation");
        event1.setEventDate(LocalDate.parse("2025-07-10"));
        event1.setRequiredSkills("Shovel,Saplings");
        event1.setUrgency(3);
        event1.setDescription("Planting trees in local park");

        EventDetails event2 = new EventDetails();
        event2.setLocation("SF");
        event2.setEventName("Beach Cleanup");
        event2.setEventDate(LocalDate.parse("2025-08-05"));
        event2.setRequiredSkills("Gloves,Bags");
        event2.setUrgency(1);
        event2.setDescription("Cleanup event at Ocean Beach");

        when(eventService.getAllEvents()).thenReturn(List.of(event1, event2));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].eventName").value("Tree Plantation"))
                .andExpect(jsonPath("$[1].eventName").value("Beach Cleanup"));
    }
}