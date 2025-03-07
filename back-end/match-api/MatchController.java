package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.services.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        String result = matchService.assignVolunteer(volunteerName, eventName);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);
        return ResponseEntity.ok(response);
    }
}
