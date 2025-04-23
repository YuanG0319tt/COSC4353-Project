package com.example.demo.service.impl;

import com.example.demo.entity.Event;
import com.example.demo.entity.VolunteerHistory;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.VolunteerHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private VolunteerHistoryService volunteerHistoryService;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventServiceImpl service;

    @Captor
    private ArgumentCaptor<VolunteerHistory> historyCaptor;

    @Test
    void save_eventInsertFails_returnsFalse() {
        Event e = new Event();
        // Simulate insert → 0 rows affected
        when(eventMapper.insert(e)).thenReturn(0);

        boolean ok = service.save(e);

        assertFalse(ok, "should return false when base save fails");
        verify(eventMapper).insert(e);
        verify(volunteerHistoryService, never()).save(any());
    }

    @Test
    void save_historyInsertFails_throws() {
        // prepare an event with all required fields
        Event e = new Event();
        e.setId(10);
        e.setDate(new Timestamp(1_234_567L));
        e.setUrgency("High");
        e.setName("MyEvent");
        e.setLocation("Here");

        // base save succeeds
        when(eventMapper.insert(e)).thenReturn(1);
        // history save fails
        when(volunteerHistoryService.save(any(VolunteerHistory.class))).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.save(e));
        assertTrue(ex.getMessage().contains("Failed to create VolunteerHistory for Event 10"));

        verify(eventMapper).insert(e);
        verify(volunteerHistoryService).save(historyCaptor.capture());

        VolunteerHistory h = historyCaptor.getValue();
        assertEquals(e.getId(), h.getEventId());
        assertEquals(e.getId(), h.getUid());
        assertEquals(e.getDate(), h.getParticipationDate());
        assertEquals(e.getUrgency(), h.getUrgency());
        assertEquals(e.getName(), h.getEventName());
        assertEquals(e.getLocation(), h.getLocation());
        assertEquals("",       h.getRole());
        assertEquals(0,        h.getHours());
        assertEquals("Pending",h.getParticipationStatus());
        assertEquals("Pending",h.getCompletionStatus());
    }

    @Test
    void save_allSucceeds_returnsTrue() {
        Event e = new Event();
        e.setId(42);
        e.setDate(new Timestamp(99_999L));
        e.setUrgency("Low");
        e.setName("Test");
        e.setLocation("There");

        when(eventMapper.insert(e)).thenReturn(1);
        when(volunteerHistoryService.save(any(VolunteerHistory.class))).thenReturn(true);

        boolean ok = service.save(e);
        assertTrue(ok, "should return true when both inserts succeed");

        verify(eventMapper).insert(e);
        verify(volunteerHistoryService).save(historyCaptor.capture());
        // we could repeat the field‐by‐field assertions if desired...
    }
}
