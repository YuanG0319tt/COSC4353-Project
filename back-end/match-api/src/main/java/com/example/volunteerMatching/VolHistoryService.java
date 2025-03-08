package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.VolHistory;
import com.example.volunteerMatching.repositories.VolHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VolHistoryService {
    private final VolHistoryRepository volHistoryRepository;

    public VolHistoryService(VolHistoryRepository volHistoryRepository) {
        this.volHistoryRepository = volHistoryRepository;
    }

    public VolHistory addVolHistory(VolHistory volHistory) {
        return volHistoryRepository.save(volHistory);
    }

    public List<VolHistory> getAllVolHistory() {
        return volHistoryRepository.findAll();
    }

    public Optional<VolHistory> getVolHistoryById(Long id) {
        return volHistoryRepository.findById(id);
    }

    public void deleteVolHistory(Long id) {
        volHistoryRepository.deleteById(id);
    }
}