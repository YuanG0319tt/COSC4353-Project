package com.example.demo.controller;

import com.example.demo.entity.DTO.VolHistory;
import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.service.VolHistoryServiceJ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteer-history")
public class VolHistoryControllerJ {
    private final VolHistoryServiceJ volHistoryService;

    public VolHistoryControllerJ(VolHistoryServiceJ volHistoryService) {
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
