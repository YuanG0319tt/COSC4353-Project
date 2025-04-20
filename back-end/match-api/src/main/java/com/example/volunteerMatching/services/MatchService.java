package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.EventDetails;
import com.example.volunteerMatching.models.UserInfo;
import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.models.VolunteerHistory;
import com.example.volunteerMatching.repositories.EventDetailsRepository;
import com.example.volunteerMatching.repositories.UserInfoRepository;
import com.example.volunteerMatching.repositories.VolunteerHistoryRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final EventDetailsRepository eventRepo;
    private final VolunteerService volunteerService;
    private final VolunteerHistoryRepository volunteerHistoryRepository;
    private final UserInfoRepository userRepo;

    public MatchService(EventDetailsRepository eventRepo,
                    UserInfoRepository userRepo,
                    VolunteerService volunteerService,
                    VolunteerHistoryRepository volunteerHistoryRepository) {
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.volunteerService = volunteerService;
        this.volunteerHistoryRepository = volunteerHistoryRepository;
    }


    public List<Map<String, String>> matchVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<EventDetails> events = eventRepo.findAll();

        List<Map<String, String>> matches = new ArrayList<>();

        for (EventDetails event : events) {
            Volunteer bestMatch = null;
            double bestScore = -1;

            for (Volunteer volunteer : volunteers) {
                double score = scoreMatch(volunteer, event);
                if (score > bestScore) {
                    bestScore = score;
                    bestMatch = volunteer;
                }
            }

            if (bestMatch != null) {
                matches.add(Map.of(
                        "eventName", event.getEventName(),
                        "volunteerName", bestMatch.getName()
                ));
            }
        }

        return matches;
    }

    public List<Map<String, String>> findBestEventsForVolunteer(String volunteerName) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<EventDetails> events = eventRepo.findAll();

        Volunteer volunteer = volunteers.stream()
                .filter(v -> v.getName().equalsIgnoreCase(volunteerName))
                .findFirst()
                .orElse(null);

        if (volunteer == null) return List.of();

        return events.stream()
                .map(event -> Map.of(
                        "volunteerName", volunteer.getName(),
                        "eventName", event.getEventName(),
                        "score", String.valueOf(scoreMatch(volunteer, event))
                ))
                .sorted((a, b) -> Double.compare(
                        Double.parseDouble(b.get("score")),
                        Double.parseDouble(a.get("score"))
                ))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> findBestVolunteersForEvent(String eventName) {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        List<EventDetails> events = eventRepo.findAll();

        EventDetails event = events.stream()
                .filter(e -> e.getEventName().equalsIgnoreCase(eventName))
                .findFirst()
                .orElse(null);

        if (event == null) return List.of();

        return volunteers.stream()
                .map(vol -> Map.of(
                        "volunteerName", vol.getName(),
                        "eventName", event.getEventName(),
                        "score", String.valueOf(scoreMatch(vol, event))
                ))
                .sorted((a, b) -> Double.compare(
                        Double.parseDouble(b.get("score")),
                        Double.parseDouble(a.get("score"))
                ))
                .limit(5)
                .collect(Collectors.toList());
    }

    private double scoreMatch(Volunteer volunteer, EventDetails event) {
        double score = 0;

        List<String> eventSkills = split(event.getRequiredSkills());
        List<String> volunteerSkills = volunteer.getSkills();
        List<String> volunteerPrefs = volunteer.getPreferences();

        // Skill match
        for (String skill : eventSkills) {
            if (volunteerSkills.contains(skill)) score += 3;
        }

        // Preference match
        for (String pref : volunteerPrefs) {
            if (eventSkills.contains(pref)) score += 2;
        }

        // Location match
        if (volunteer.getLocation() != null &&
                volunteer.getLocation().equalsIgnoreCase(event.getLocation())) {
            score += 1;
        }

        // Availability match
        if (volunteer.getAvailability() != null) {
            try {
                LocalDate availDate = LocalDate.parse(volunteer.getAvailability());
                long days = Math.abs(ChronoUnit.DAYS.between(availDate, event.getEventDate()));
                score += Math.max(0, 2 - (days / 2.0));
            } catch (Exception ignored) {}
        }

        // Urgency weight
        score += event.getUrgency() * 0.5;

        return score;
    }

    private List<String> split(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    public String assignVolunteer(String volunteerName, String eventName) {
        Optional<UserInfo> userOpt = userRepo.findAll().stream()
                .filter(v -> volunteerName.equalsIgnoreCase(v.getName()))
                .findFirst();
    
        Optional<EventDetails> eventOpt = eventRepo.findAll().stream()
                .filter(e -> eventName.equalsIgnoreCase(e.getEventName()))
                .findFirst();
    
        if (userOpt.isPresent() && eventOpt.isPresent()) {
            UserInfo user = userOpt.get();
            EventDetails event = eventOpt.get();
    
            // Save to VolunteerHistory
            VolunteerHistory history = new VolunteerHistory();
            history.setUid(user.getUserId().intValue());
            history.setEventId(event.getEventID());
            history.setParticipationDate(LocalDate.now());
            history.setHoursVolunteered(0.0);
            history.setParticipationStatus("Pending");
    
            volunteerHistoryRepository.save(history);
    
            return user.getName() + " assigned to " + event.getEventName();
        }
    
        return "Assignment failed. Please try again.";
    }    
}
