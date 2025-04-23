package com.example.demo.service;

import com.example.demo.entity.EventDetails;
import com.example.demo.repositories.EventDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceJ {

    private final EventDetailsRepository repository;

    public EventServiceJ(EventDetailsRepository repository) {
        this.repository = repository;
    }

    public EventDetails addEvent(EventDetails event) {
        return repository.save(event);
    }

    public List<EventDetails> getAllEvents() {
        List<EventDetails> events = repository.findAll();
        System.out.println("=== Repository Test ===");
        System.out.println("Found " + events.size() + " events from DB");
        return events;
    }

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
    
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
