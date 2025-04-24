package com.example.demo.controller;

import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.service.VolHistoryServiceJ;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

import com.example.demo.entity.DTO.VolHistory;

@RestController
@RequestMapping("/volunteer-history")
public class VolunteerHistoryController {

    private final VolHistoryServiceJ service;

    public VolunteerHistoryController(VolHistoryServiceJ service) {
        this.service = service;
    }

    @GetMapping
    public List<VolHistoryProjection> getAllHistory() {
        return service.getAllVolHistory();
    }

    // ✅ POST for adding a volunteer history
    @PostMapping
    public VolHistory add(@RequestBody VolHistory dto) {
        return service.addVolHistory(dto);
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<VolHistoryProjection> getById(@PathVariable Long id) {
        return service.getVolHistoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ DELETE by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ResponseEntity<Void> response = ResponseEntity.noContent().build();
        return service.getVolHistoryById(id)
                .map(p -> {
                    service.deleteVolHistory(id);
                    return response;
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
