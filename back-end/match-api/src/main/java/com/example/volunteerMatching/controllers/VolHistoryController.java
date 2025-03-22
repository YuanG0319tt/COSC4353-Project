package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.VolHistory;
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
    public List<VolHistory> getAllVolunteerHistory() {
        return volHistoryService.getAllVolHistory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolHistory> getVolunteerHistoryById(@PathVariable Long id) {
        Optional<VolHistory> volHistory = volHistoryService.getVolHistoryById(id);
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
