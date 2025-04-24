package com.example.demo.service;

import com.example.demo.entity.DTO.VolHistory;
import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.entity.VolunteerHistoryJ;
import com.example.demo.repositories.VolunteerHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VolHistoryServiceJTest {

    @Mock
    private VolunteerHistoryRepository repo;

    @InjectMocks
    private VolHistoryServiceJ service;

    private VolHistory dto;
    private VolunteerHistoryJ savedEntity;

    @BeforeEach
    void setup() {
        // prepare a DTO template
        dto = new VolHistory();
        dto.setEventDate("2025-04-22");
        dto.setHoursVolunteered(2);

        // prepare a saved entity template
        savedEntity = new VolunteerHistoryJ();
        savedEntity.setHistoryId(99);
        savedEntity.setParticipationStatus("DONE");
    }

    @Test
    void getAllVolHistory_delegatesToRepository() {
        List<VolHistoryProjection> list = List.of(mock(VolHistoryProjection.class));
        when(repo.fetchVolunteerHistoryTable()).thenReturn(list);

        List<VolHistoryProjection> out = service.getAllVolHistory();
        assertSame(list, out);
        verify(repo).fetchVolunteerHistoryTable();
    }

    @Test
    void getVolHistoryById_whenFound() {
        VolHistoryProjection proj = mock(VolHistoryProjection.class);
        when(repo.fetchVolunteerHistoryById(5L))
                .thenReturn(Optional.of(proj));

        Optional<VolHistoryProjection> opt = service.getVolHistoryById(5L);
        assertTrue(opt.isPresent());
        assertSame(proj, opt.get());
        verify(repo).fetchVolunteerHistoryById(5L);
    }

    @Test
    void getVolHistoryById_whenNotFound() {
        when(repo.fetchVolunteerHistoryById(6L))
                .thenReturn(Optional.empty());

        Optional<VolHistoryProjection> opt = service.getVolHistoryById(6L);
        assertTrue(opt.isEmpty());
        verify(repo).fetchVolunteerHistoryById(6L);
    }

    @Test
    void addVolHistory_withNullDtoId_setsDateAndHoursAndReturnsDto() {
        // dto.id is initially null
        // stub repository.save to return our savedEntity
        when(repo.save(any(VolunteerHistoryJ.class))).thenReturn(savedEntity);

        VolHistory out = service.addVolHistory(dto);

        // dto.id should now be savedEntity.historyId
        assertEquals(99L, out.getId());
        assertEquals("DONE", out.getStatus());

        // capture the entity passed into save(...)
        ArgumentCaptor<VolunteerHistoryJ> cap = ArgumentCaptor.forClass(VolunteerHistoryJ.class);
        verify(repo).save(cap.capture());
        VolunteerHistoryJ ent = cap.getValue();

        // Because dto.getId() was null, ent.historyId should be null
        assertNull(ent.getHistoryId());
        assertEquals(LocalDate.parse("2025-04-22"), ent.getParticipationDate());
        assertEquals(2, ent.getHoursVolunteered());
    }

    @Test
    void addVolHistory_withNonNullDtoId_preservesHistoryId() {
        dto.setId(7L);
        when(repo.save(any(VolunteerHistoryJ.class))).thenReturn(savedEntity);

        VolHistory out = service.addVolHistory(dto);
        assertEquals(99L, out.getId());

        ArgumentCaptor<VolunteerHistoryJ> cap = ArgumentCaptor.forClass(VolunteerHistoryJ.class);
        verify(repo).save(cap.capture());
        VolunteerHistoryJ ent = cap.getValue();

        // Because dto.id was 7, ent.historyId should be set to 7
        assertEquals(7, ent.getHistoryId());
    }

    @Test
    void deleteVolHistory_delegatesToRepository() {
        // just ensure no exception
        service.deleteVolHistory(123L);
        verify(repo).deleteById(123L);
    }
}
