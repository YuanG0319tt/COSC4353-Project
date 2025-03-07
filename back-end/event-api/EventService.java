package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Event;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final List<Event> events = new ArrayList<>();

    public Event addEvent(Event event) {
        events.add(event);
        return event;
    }

    public List<Event> getAllEvents() {
        return events;
    }
}
