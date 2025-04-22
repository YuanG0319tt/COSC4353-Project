package com.example.demo.controller;

import com.example.demo.entity.Event;
import com.example.demo.service.EventService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Provide a mock implementation for the service dependency.
    @MockBean
    private EventService eventService;

    /**
     * Test for GET /api/events/list endpoint.
     */
    @Test
    public void testListEvents() throws Exception {
        // Arrange: Prepare a sample Event and a list to be returned.
        Event event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("Test description");
        event.setLocation("Test Location");
        // For simplicity, date is set to null; adjust as needed.
        event.setDate(null);
        event.setRequiredSkills("Testing skills");
        event.setUrgency("High");

        List<Event> eventsList = Arrays.asList(event);
        when(eventService.list()).thenReturn(eventsList);

        // Act & Assert: Perform the GET request and verify the JSON response.
        mockMvc.perform(get("/api/events/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("Test Event"));
    }

    /**
     * Test for POST /api/events/add endpoint when saving an event is successful.
     */
    @Test
    public void testAddEvent_Success() throws Exception {
        // Arrange: Sample JSON payload for a new event.
        String eventJson = "{" +
                "\"name\":\"New Event\"," +
                "\"description\":\"New event description\"," +
                "\"location\":\"New Location\"," +
                "\"date\":\"Apr 14, 2025\"," +
                "\"requiredSkills\":\"Skill1, Skill2\"," +
                "\"urgency\":\"Medium\"" +
                "}";
        // Stub the save call to return true.
        when(eventService.save(any(Event.class))).thenReturn(true);

        // Act & Assert: Perform the POST request and validate the response.
        mockMvc.perform(post("/api/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(jsonPath("$.code").value(HttpStatus.SUCCESS));
    }

    /**
     * Test for POST /api/events/add endpoint when saving an event fails.
     */
    @Test
    public void testAddEvent_Failure() throws Exception {
        // Arrange: Same JSON payload as the success case.
        String eventJson = "{" +
                "\"name\":\"New Event\"," +
                "\"description\":\"New event description\"," +
                "\"location\":\"New Location\"," +
                "\"date\":\"Apr 14, 2025\"," +
                "\"requiredSkills\":\"Skill1, Skill2\"," +
                "\"urgency\":\"Medium\"" +
                "}";
        // Stub the save call to return false to simulate a failure.
        when(eventService.save(any(Event.class))).thenReturn(false);

        // Act & Assert: Perform the POST request and expect failure output.
        mockMvc.perform(post("/api/events/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(eventJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value(HttpMessage.INSERT_FAILED))
                .andExpect(jsonPath("$.code").value(HttpStatus.FAILED));
    }
}
