package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.DTO.VolHistoryProjection;
import com.example.volunteerMatching.models.DTO.VolHistory;
import com.example.volunteerMatching.models.VolunteerHistory;
import com.example.volunteerMatching.repositories.VolunteerHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolHistoryService {

    private final VolunteerHistoryRepository volunteerHistoryRepository;

    public VolHistoryService(VolunteerHistoryRepository volunteerHistoryRepository) {
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
        VolunteerHistory entity = new VolunteerHistory();

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

        VolunteerHistory saved = volunteerHistoryRepository.save(entity);

        // Return a basic DTO if needed (or fetch again with full join if preferred)
        dto.setId((long) saved.getHistoryId());
        dto.setStatus("Completed");
        return dto;
    }

    // Delete by ID
    public void deleteVolHistory(Long id) {
        volunteerHistoryRepository.deleteById(id);
    }
}
