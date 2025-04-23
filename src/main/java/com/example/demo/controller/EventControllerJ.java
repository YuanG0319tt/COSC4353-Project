package com.example.demo.controller;

import com.example.demo.entity.EventDetails;
import com.example.demo.service.EventServiceJ;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventControllerJ {

    private final EventServiceJ service;

    public EventControllerJ(EventServiceJ service) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable("id") Integer id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDetails> updateEvent(@PathVariable("id") int id, @RequestBody EventDetails updatedEvent) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedEvent.setEventID(id); // make sure ID is set
        EventDetails saved = service.addEvent(updatedEvent); // .save acts as upsert
        return ResponseEntity.ok(saved);
    }
}
