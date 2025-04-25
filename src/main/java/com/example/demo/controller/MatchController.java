package com.example.demo.controller;

import com.example.demo.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public List<Map<String, String>> getMatches() {
        return matchService.matchVolunteers();
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> assignVolunteer(@RequestBody Map<String, String> body) {
        String volunteerName = body.get("volunteerName");
        String eventName = body.get("eventName");

        if (volunteerName == null || eventName == null ||
            volunteerName.isBlank() || eventName.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Invalid request data"));
        }

        String result = matchService.assignVolunteer(volunteerName, eventName);
        return ResponseEntity.ok(Map.of("message", result));
    }
    
    @GetMapping("/match/volunteer")
    public List<Map<String, String>> getMatchesForVolunteer(@RequestParam String name) {
        return matchService.findBestEventsForVolunteer(name);
    }

    @GetMapping("/match/event")
    public List<Map<String, String>> getMatchesForEvent(@RequestParam String name) {
        return matchService.findBestVolunteersForEvent(name);
    }
    
    @GetMapping("/top-events")
    public List<Map<String, String>> topEvents(@RequestParam String volunteer) {
        return matchService.findBestEventsForVolunteer(volunteer);
    }

    @GetMapping("/top-volunteers")
    public List<Map<String, String>> topVolunteers(@RequestParam String event) {
        return matchService.findBestVolunteersForEvent(event);
    }
}
