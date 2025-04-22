package com.example.demo.controller;

import com.example.demo.entity.EventDetails;
import com.example.demo.service.EventServiceJ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventControllerJ.class)
class EventControllerJTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventServiceJ service;

    private static final ObjectMapper mapper = new ObjectMapper();

    private String toJson(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    @Test
    void addEventReturnsCreatedEntity() throws Exception {
        EventDetails in = new EventDetails();
        in.setEventName("New");
        EventDetails out = new EventDetails();
        out.setEventID(1);
        out.setEventName("New");

        when(service.addEvent(any(EventDetails.class))).thenReturn(out);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventID").value(1))
                .andExpect(jsonPath("$.eventName").value("New"));

        verify(service).addEvent(any(EventDetails.class));
    }

    @Test
    void getAllEventsReturnsList() throws Exception {
        EventDetails e1 = new EventDetails(); e1.setEventID(1);
        EventDetails e2 = new EventDetails(); e2.setEventID(2);
        when(service.getAllEvents()).thenReturn(Arrays.asList(e1, e2));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventID").value(1))
                .andExpect(jsonPath("$[1].eventID").value(2));

        verify(service).getAllEvents();
    }

    @Test
    void deleteEventNotFound() throws Exception {
        when(service.existsById(5)).thenReturn(false);

        mockMvc.perform(delete("/events/5"))
                .andExpect(status().isNotFound());

        verify(service).existsById(5);
        verify(service, never()).deleteById(anyInt());
    }

    @Test
    void deleteEventSuccess() throws Exception {
        when(service.existsById(6)).thenReturn(true);

        mockMvc.perform(delete("/events/6"))
                .andExpect(status().isNoContent());

        verify(service).existsById(6);
        verify(service).deleteById(6);
    }

    @Test
    void updateEventNotFound() throws Exception {
        EventDetails upd = new EventDetails();
        upd.setEventName("X");
        when(service.existsById(7)).thenReturn(false);

        mockMvc.perform(put("/events/7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(upd)))
                .andExpect(status().isNotFound());

        verify(service).existsById(7);
        verify(service, never()).addEvent(any());
    }

    @Test
    void updateEventSuccess() throws Exception {
        EventDetails upd = new EventDetails();
        upd.setEventName("Y");
        EventDetails saved = new EventDetails();
        saved.setEventID(8);
        saved.setEventName("Y");

        when(service.existsById(8)).thenReturn(true);
        when(service.addEvent(any(EventDetails.class))).thenReturn(saved);

        mockMvc.perform(put("/events/8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(upd)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventID").value(8))
                .andExpect(jsonPath("$.eventName").value("Y"));

        verify(service).existsById(8);
        verify(service).addEvent(argThat(e -> e.getEventID() == 8 && "Y".equals(e.getEventName())));
    }
}
