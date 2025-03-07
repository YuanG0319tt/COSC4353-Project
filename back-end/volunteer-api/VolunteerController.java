package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.Volunteer;
import com.example.volunteerMatching.services.VolunteerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {
    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping
    public Volunteer addVolunteer(@RequestBody Volunteer volunteer) {
        return volunteerService.addVolunteer(volunteer);
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerService.getAllVolunteers();
    }
}
