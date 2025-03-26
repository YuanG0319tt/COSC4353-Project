package com.example.volunteerMatching;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.repositories.EventDetailsRepository;
import com.example.volunteerMatching.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
// import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventDetailsRepository eventDetailsRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addEvent_shouldReturnSavedEvent() {
        EventDetails event = new EventDetails();
        event.setLocation("Houston");
        event.setEventName("Food Drive");
        event.setEventDate(LocalDate.of(2025, 3, 7));
        event.setRequiredSkills("Packing,Lifting,Organizing");
        event.setUrgency(5);
        event.setDescription("Helping pack and deliver food to Houston residents.");

        when(eventDetailsRepository.save(any(EventDetails.class))).thenReturn(event);

        EventDetails savedEvent = eventService.addEvent(event);

        assertNotNull(savedEvent);
        assertEquals("Food Drive", savedEvent.getEventName());
        verify(eventDetailsRepository, times(1)).save(event);
    }

    @Test
    void getAllEvents_shouldReturnListOfEvents() {
        EventDetails event1 = new EventDetails();
        event1.setEventName("Tree Planting");

        EventDetails event2 = new EventDetails();
        event2.setEventName("Beach Cleanup");

        when(eventDetailsRepository.findAll()).thenReturn(List.of(event1, event2));

        List<EventDetails> events = eventService.getAllEvents();

        assertEquals(2, events.size());
        assertEquals("Tree Planting", events.get(0).getEventName());
        assertEquals("Beach Cleanup", events.get(1).getEventName());
        verify(eventDetailsRepository, times(1)).findAll();
    }
}