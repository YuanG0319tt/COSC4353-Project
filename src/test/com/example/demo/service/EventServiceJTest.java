package com.example.demo.service;

import com.example.demo.entity.EventDetails;
import com.example.demo.repositories.EventDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class EventServiceJTest {

    @Mock
    private EventDetailsRepository repository;

    @InjectMocks
    private EventServiceJ service;

    @Test
    void testAddEvent() {
        EventDetails event = new EventDetails();
        event.setEventName("Test Event");
        when(repository.save(event)).thenReturn(event);

        EventDetails result = service.addEvent(event);
        assertSame(event, result, "service.addEvent should return exactly what repository.save returned");
        verify(repository).save(event);
    }

    @Test
    void testGetAllEvents() {
        EventDetails e1 = new EventDetails(); e1.setEventID(1);
        EventDetails e2 = new EventDetails(); e2.setEventID(2);
        List<EventDetails> mockList = Arrays.asList(e1, e2);
        when(repository.findAll()).thenReturn(mockList);

        List<EventDetails> result = service.getAllEvents();
        assertEquals(2, result.size(), "Should return both events");
        assertSame(mockList, result, "Should return the exact list from repository");
        verify(repository).findAll();
    }

    @Test
    void testExistsById() {
        when(repository.existsById(42)).thenReturn(true);
        assertTrue(service.existsById(42));
        verify(repository).existsById(42);
    }

    @Test
    void testDeleteById() {
        // deleteById returns void
        service.deleteById(99);
        verify(repository).deleteById(99);
    }
}
