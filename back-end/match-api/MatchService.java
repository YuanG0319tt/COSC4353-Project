package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Event;
import com.example.volunteerMatching.models.Volunteer;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {
    private final VolunteerService volunteerService;
    private final EventService eventService;

    public MatchService(VolunteerService volunteerService, EventService eventService) {
        this.volunteerService = volunteerService;
        this.eventService = eventService;
    }

    public List<String> matchVolunteers() {
        List<Event> events = eventService.getAllEvents();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<String> matches = new ArrayList<>();

        for (Event event : events) {
            Volunteer bestMatch = null;
            int highestMatchScore = 0;

            for (Volunteer volunteer : volunteers) {
                int matchScore = (int) volunteer.getSkills().stream()
                    .filter(event.getRequirements()::contains).count();
                
                if (matchScore > highestMatchScore) {
                    highestMatchScore = matchScore;
                    bestMatch = volunteer;
                }
            }

            if (bestMatch != null) {
                matches.add(event.getName() + " -> " + bestMatch.getName());
            }
        }
        return matches;
    }

    public String assignVolunteer(String volunteerName, String eventName) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<Event> events = eventService.getAllEvents();

        Volunteer selectedVolunteer = volunteers.stream()
            .filter(v -> v.getName().equals(volunteerName))
            .findFirst().orElse(null);

        Event selectedEvent = events.stream()
            .filter(e -> e.getName().equals(eventName))
            .findFirst().orElse(null);

        if (selectedVolunteer != null && selectedEvent != null) {
            return selectedVolunteer.getName() + " assigned to " + selectedEvent.getName();
        }
        return "Assignment failed. Please try again.";
    }

}

