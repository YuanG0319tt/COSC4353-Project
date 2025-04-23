package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.Event;
import com.example.demo.entity.VolunteerHistory;
import com.example.demo.mapper.EventMapper;
import com.example.demo.mapper.VolunteerHistoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolunteerHistoryServiceImplTest {

    @Mock
    private VolunteerHistoryMapper volunteerHistoryMapper;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private VolunteerHistoryServiceImpl service;

    private VolunteerHistory sampleHistory;
    private Event sampleEvent;

    @BeforeEach
    void setUp() {
        sampleHistory = new VolunteerHistory();
        sampleHistory.setEventId(42);

        sampleEvent = new Event();
        sampleEvent.setId(42);
        sampleEvent.setName("Test Event");
        sampleEvent.setDescription("Desc");
        sampleEvent.setLocation("Loc");
        sampleEvent.setRequiredSkills("S1,S2");
        sampleEvent.setUrgency("High");
    }

    // 1) No filters: uid=0, status=null, eventOrRole=null
    @Test
    void selectList_noFilters_delegatesToMapper() {
        List<VolunteerHistory> expected = List.of(new VolunteerHistory());
        when(volunteerHistoryMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(expected);

        List<VolunteerHistory> actual = service.selectList(null, null, 0);

        assertSame(expected, actual);
        verify(volunteerHistoryMapper).selectList(any(LambdaQueryWrapper.class));
    }

    // 2) Only uid filter
    @Test
    void selectList_uidFilter_appliesUidCondition() {
        List<VolunteerHistory> expected = List.of();
        when(volunteerHistoryMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(expected);

        List<VolunteerHistory> actual = service.selectList("", "", 7);

        assertSame(expected, actual);
        verify(volunteerHistoryMapper).selectList(any(LambdaQueryWrapper.class));
    }

    // 3) Only status filter
    @Test
    void selectList_statusFilter_appliesStatusCondition() {
        List<VolunteerHistory> expected = List.of();
        when(volunteerHistoryMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(expected);

        List<VolunteerHistory> actual = service.selectList("", "COMPLETED", 0);

        assertSame(expected, actual);
        verify(volunteerHistoryMapper).selectList(any(LambdaQueryWrapper.class));
    }

    // 4) Only eventOrRole filter
    @Test
    void selectList_eventOrRoleFilter_appliesLikeCondition() {
        List<VolunteerHistory> expected = List.of();
        when(volunteerHistoryMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(expected);

        List<VolunteerHistory> actual = service.selectList("searchTerm", "", 0);

        assertSame(expected, actual);
        verify(volunteerHistoryMapper).selectList(any(LambdaQueryWrapper.class));
    }

    // 5) All filters together
    @Test
    void selectList_allFilters_appliesAllConditions() {
        List<VolunteerHistory> expected = List.of();
        when(volunteerHistoryMapper.selectList(any(LambdaQueryWrapper.class)))
                .thenReturn(expected);

        List<VolunteerHistory> actual = service.selectList("foo", "DONE", 99);

        assertSame(expected, actual);
        verify(volunteerHistoryMapper).selectList(any(LambdaQueryWrapper.class));
    }

    // 6) add(...) returns true when insert > 0
    @Test
    void add_returnsTrue_whenInsertSucceeds() {
        // stub event lookup
        when(eventMapper.selectById(42)).thenReturn(sampleEvent);
        // stub insert
        when(volunteerHistoryMapper.insert(sampleHistory)).thenReturn(1);

        boolean result = service.add(sampleHistory);

        assertTrue(result);
        // after add(), history should be populated from event
        assertEquals("Test Event", sampleHistory.getEventName());
        assertEquals("Desc",        sampleHistory.getDescription());
        assertEquals("Loc",         sampleHistory.getLocation());
        assertEquals("S1,S2",       sampleHistory.getRequiredSkills());
        assertEquals("High",        sampleHistory.getUrgency());
        verify(eventMapper).selectById(42);
        verify(volunteerHistoryMapper).insert(sampleHistory);
    }

    // 7) add(...) returns false when insert == 0
    @Test
    void add_returnsFalse_whenInsertFails() {
        when(eventMapper.selectById(42)).thenReturn(sampleEvent);
        when(volunteerHistoryMapper.insert(sampleHistory)).thenReturn(0);

        boolean result = service.add(sampleHistory);

        assertFalse(result);
        // fields should still be set even on failure
        assertEquals("Test Event", sampleHistory.getEventName());
        verify(eventMapper).selectById(42);
        verify(volunteerHistoryMapper).insert(sampleHistory);
    }
}
