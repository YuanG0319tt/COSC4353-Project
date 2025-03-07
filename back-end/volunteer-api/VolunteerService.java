package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Volunteer;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class VolunteerService {
    private final List<Volunteer> volunteers = new ArrayList<>();

    public Volunteer addVolunteer(Volunteer volunteer) {
        volunteers.add(volunteer);
        return volunteer;
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteers;
    }
}
