package com.example.demo.service;

import com.example.demo.entity.DTO.VolHistory;
import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.entity.VolunteerHistoryJ;
import com.example.demo.repositories.VolunteerHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolHistoryServiceJ {

    private final VolunteerHistoryRepository volunteerHistoryRepository;

    public VolHistoryServiceJ(VolunteerHistoryRepository volunteerHistoryRepository) {
        this.volunteerHistoryRepository = volunteerHistoryRepository;
    }

    // Get all volunteer history records using native DTO query
    public List<VolHistoryProjection> getAllVolHistory() {
        return volunteerHistoryRepository.fetchVolunteerHistoryTable();
    }

    // Get a specific volunteer history record by ID using native DTO query
    public Optional<VolHistoryProjection> getVolHistoryById(Long id) {
        return volunteerHistoryRepository.fetchVolunteerHistoryById(id);
    }

    // Add new volunteer history record from DTO input (simplified version)
    public VolHistory addVolHistory(VolHistory dto) {
        VolunteerHistoryJ entity = new VolunteerHistoryJ();

        if (dto.getId() != null) {
            entity.setHistoryId(dto.getId().intValue());
        }

        // You'll need to map email → UserID, and eventName → EventID manually
        // This assumes dto.getEventDate() is properly formatted as YYYY-MM-DD
        entity.setParticipationDate(java.time.LocalDate.parse(dto.getEventDate()));
        entity.setHoursVolunteered((double) dto.getHoursVolunteered());

        // You must look up and set uid and eventId manually
        // entity.setUid(...);
        // entity.setEventId(...);

        VolunteerHistoryJ saved = volunteerHistoryRepository.save(entity);

        // Return a basic DTO if needed (or fetch again with full join if preferred)
        dto.setId((long) saved.getHistoryId());
        dto.setStatus((String) saved.getParticipationStatus());
        return dto;
    }

    // Delete by ID
    public void deleteVolHistory(Long id) {
        volunteerHistoryRepository.deleteById(id);
    }
}
