package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.DTO.VolHistoryProjection;
import com.example.volunteerMatching.models.DTO.VolHistory;
import com.example.volunteerMatching.services.VolHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteer-history")
public class VolHistoryController {
    private final VolHistoryService volHistoryService;

    public VolHistoryController(VolHistoryService volHistoryService) {
        this.volHistoryService = volHistoryService;
    }

    @PostMapping
    public VolHistory addVolunteerHistory(@RequestBody VolHistory volHistory) {
        return volHistoryService.addVolHistory(volHistory);
    }

    @GetMapping
    public List<VolHistoryProjection> getAllVolunteerHistory() {
        return volHistoryService.getAllVolHistory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolHistoryProjection> getVolunteerHistoryById(@PathVariable Long id) {
        Optional<VolHistoryProjection> volHistory = volHistoryService.getVolHistoryById(id);
        return volHistory.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteerHistory(@PathVariable Long id) {
        if (volHistoryService.getVolHistoryById(id).isPresent()) {
            volHistoryService.deleteVolHistory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
