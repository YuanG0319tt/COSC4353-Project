package com.example.demo.service;

import com.example.demo.entity.Volunteer;
import com.example.demo.mapper.UserProfileMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerService {

    private final UserProfileMapper userProfileMapper;

    public VolunteerService(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    public List<Volunteer> getAllVolunteers() {
        return userProfileMapper.selectList(null).stream().map(profile -> {
            Volunteer volunteer = new Volunteer();
            volunteer.setName(profile.getFullName());                        // from full_name column
            volunteer.setSkills(split(profile.getSkills()));                // CSV → List
            volunteer.setPreferences(split(profile.getPreferences()));      // CSV → List
            volunteer.setLocation(profile.getCity());                       // city used as location
            volunteer.setAvailability(profile.getAvailability());
            return volunteer;
        }).collect(Collectors.toList());
    }

    private List<String> split(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();
        return Arrays.stream(csv.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
