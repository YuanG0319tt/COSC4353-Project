package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.EventDetails;
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
        List<EventDetails> events = eventService.getAllEvents();
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<String> matches = new ArrayList<>();
    
        for (EventDetails event : events) {
            Volunteer bestMatch = null;
            int highestMatchScore = 0;
    
            for (Volunteer volunteer : volunteers) {
                int matchScore = 0;
    
                boolean hasSkillMatch = volunteer.getSkills().stream()
                    .anyMatch(event.getRequiredSkills()::contains);
    
                if (!hasSkillMatch) {
                    continue;
                }
    
                if (volunteer.getLocation().equalsIgnoreCase(event.getLocation())) {
                    matchScore += 3;
                }
    
                if (volunteer.getAvailability().equals(event.getEventDate())) {
                    matchScore += 2;
                }
    
                matchScore += (int) volunteer.getSkills().stream()
                    .filter(event.getRequiredSkills()::contains)
                    .count() * 2;
    
                if (volunteer.getPreferences().stream().anyMatch(event.getRequiredSkills()::contains)) {
                    matchScore += 1;
                }
    
                if (matchScore > highestMatchScore) {
                    highestMatchScore = matchScore;
                    bestMatch = volunteer;
                }
            }
    
            if (bestMatch != null) {
                matches.add(event.getEventName() + " -> " + bestMatch.getName());
            }
        }
        return matches;
    }    

    public String assignVolunteer(String volunteerName, String eventName) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<EventDetails> events = eventService.getAllEvents();

        Volunteer selectedVolunteer = volunteers.stream()
            .filter(v -> v.getName().equals(volunteerName))
            .findFirst().orElse(null);

        EventDetails selectedEvent = events.stream()
            .filter(e -> e.getEventName().equals(eventName))
            .findFirst().orElse(null);

        if (selectedVolunteer != null && selectedEvent != null) {
            return selectedVolunteer.getName() + " assigned to " + selectedEvent.getEventName();
        }
        return "Assignment failed. Please try again.";
    }

}