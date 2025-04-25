package com.example.demo.service;

import com.example.demo.entity.EventDetails;
import com.example.demo.entity.UserProfile;
import com.example.demo.entity.Volunteer;
import com.example.demo.entity.VolunteerHistory;
import com.example.demo.entity.VolunteerHistoryJ;
import com.example.demo.repositories.EventDetailsRepository;
import com.example.demo.mapper.UserProfileMapper;
import com.example.demo.mapper.VolunteerHistoryMapper;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.sql.Date;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final EventDetailsRepository eventRepo;
    private final VolunteerService volunteerService;
    private final VolunteerHistoryMapper volunteerHistoryMapper;
    private final UserProfileMapper userProfileMapper;

    public MatchService(EventDetailsRepository eventRepo,
                        UserProfileMapper userProfileMapper,
                        VolunteerService volunteerService,
                        VolunteerHistoryMapper volunteerHistoryMapper) {
        this.eventRepo = eventRepo;
        this.userProfileMapper = userProfileMapper;
        this.volunteerService = volunteerService;
        this.volunteerHistoryMapper = volunteerHistoryMapper;
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
    
        Volunteer volunteer = volunteers.stream()
            .filter(v -> v.getName().equalsIgnoreCase(volunteerName))
            .findFirst()
            .orElse(null);
    
        if (volunteer == null) {
            System.err.println("⚠️ No volunteer found with name: " + volunteerName);
            return List.of(); // ✅ return empty list instead of causing a crash
        }
    
        List<EventDetails> events = eventRepo.findAll();
    
        return events.stream()
            .map(event -> {
                Map<String, String> result = new HashMap<>();
                result.put("volunteerName", Objects.toString(volunteer.getName(), "-"));
                result.put("eventName", Objects.toString(event.getEventName(), "-"));
                result.put("score", String.valueOf(scoreMatch(volunteer, event)));
                return result;
            })
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
        .map(vol -> {
            Map<String, String> result = new HashMap<>();
            result.put("volunteerName", Objects.toString(vol.getName(), "-"));
            result.put("eventName", Objects.toString(event.getEventName(), "-"));
            result.put("score", String.valueOf(scoreMatch(vol, event)));
            return result;
        })        
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
                LocalDate availability = LocalDate.parse(volunteer.getAvailability());
                LocalDate eventDate = event.getEventDate().toLocalDate();

                long days = Math.abs(ChronoUnit.DAYS.between(availability, eventDate));
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
        System.out.println("🚀 assignVolunteer called with: " + volunteerName + " / " + eventName);
    
        UserProfile user = userProfileMapper.selectList(null).stream()
            .filter(v -> volunteerName.equalsIgnoreCase(v.getFullName()))
            .findFirst()
            .orElse(null);
    
        Optional<EventDetails> eventOpt = eventRepo.findByEventNameIgnoreCase(eventName);

        if (user == null || eventOpt.isEmpty()) {
            return "Assignment failed. Please check the names and try again.";
        }
    
        EventDetails event = eventOpt.get();
    
        // 🛑 Check for existing match
        List<VolunteerHistory> existing = volunteerHistoryMapper.findByNameAndEvent(volunteerName, eventName);
        if (!existing.isEmpty()) {
            return "This volunteer is already assigned to this event.";
        }
    
        VolunteerHistory history = new VolunteerHistory();
        history.setUserId(user.getUid());
        history.setEventId(event.getEventID());
        history.setEventName(event.getEventName());
        history.setName(user.getFullName());
        Date eventDate = event.getEventDate(); // java.sql.Date
if (eventDate == null) {
    System.out.println("⚠️ Event date is NULL");
} else {
    System.out.println("📅 Event date: " + eventDate.toString());
    history.setParticipationDate(eventDate);
}

        history.setHoursVolunteered(0);
        history.setStatus("Pending");
        int inserted = volunteerHistoryMapper.insertHistory(history);
        if (inserted == 0) {
            return "❌ Insert failed.";
        }
    
        return user.getFullName() + " assigned to " + event.getEventName();
    }
    
}
