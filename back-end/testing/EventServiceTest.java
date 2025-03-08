package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EventServiceTest {
    private EventService eventService;

    @BeforeEach
    void setUp() {
        eventService = new EventService();
    }

    @Test
    void addEvent_shouldAddEvent() {
        Event event = new Event("Houston", "Food Drive", "2025-03-07", List.of("Packing","Lifting","Organizing"), "High", "Helping pack and deliver food to Houston residents.");
        Event addedEvent = eventService.addEvent(event);

        assertEquals(event, addedEvent);
        assertEquals(1, eventService.getAllEvents().size());
    }

    @Test
    void getAllEvents_shouldReturnEvents() {
        assertTrue(eventService.getAllEvents().isEmpty());
    }
}