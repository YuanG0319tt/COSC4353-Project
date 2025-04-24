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

    public List<VolHistoryProjection> getAllVolHistory() {
        return volunteerHistoryRepository.fetchVolunteerHistoryTable();
    }

    public Optional<VolHistoryProjection> getVolHistoryById(Long id) {
        return volunteerHistoryRepository.fetchVolunteerHistoryById(id);
    }

    public VolHistory addVolHistory(VolHistory dto) {
        VolunteerHistoryJ entity = new VolunteerHistoryJ();

        if (dto.getId() != null) {
            entity.setHistoryId(dto.getId().intValue());
        }

        entity.setParticipationDate(java.time.LocalDate.parse(dto.getEventDate()));
        entity.setHoursVolunteered((int) dto.getHoursVolunteered());


        VolunteerHistoryJ saved = volunteerHistoryRepository.save(entity);

        dto.setId((long) saved.getHistoryId());
        dto.setStatus((String) saved.getParticipationStatus());
        return dto;
    }

    // Delete by ID
    public void deleteVolHistory(Long id) {
        volunteerHistoryRepository.deleteById(id);
    }   
}
