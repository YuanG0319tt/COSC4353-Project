package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.repositories.EventDetailsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    private final EventDetailsRepository repository;

    public EventService(EventDetailsRepository repository) {
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
}
