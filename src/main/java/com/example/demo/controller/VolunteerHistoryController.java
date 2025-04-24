package com.example.demo.controller;

import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.service.VolHistoryServiceJ;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer-history")
public class VolunteerHistoryController {

    private final VolHistoryServiceJ service;

    public VolunteerHistoryController(VolHistoryServiceJ service) {
        this.service = service;
    }

    @GetMapping
    public List<VolHistoryProjection> getAllHistory() {
        return service.getAllVolHistory(); // Uses the SQL projection
    }
}
