package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @PostMapping
    public EventDetails addEvent(@RequestBody EventDetails event) {
        return service.addEvent(event);
    }

    @GetMapping
    public List<EventDetails> getAllEvents() {
        return service.getAllEvents();
    }
}
