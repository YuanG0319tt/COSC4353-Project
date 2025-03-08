package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.services.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;

@RestController
@RequestMapping("/match")
public class MatchController {
    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<String> getMatches() {
        return matchService.matchVolunteers();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> matchVolunteer(@RequestBody Map<String, String> matchRequest) {
        String volunteerName = matchRequest.get("volunteerName");
        String eventName = matchRequest.get("eventName");
    
        if (volunteerName == null || eventName == null || volunteerName.isBlank() || eventName.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Missing required fields: volunteerName and eventName"));
        }
    
        String result = matchService.assignVolunteer(volunteerName, eventName);
        return ResponseEntity.ok(Map.of("message", result));
    }    
}
