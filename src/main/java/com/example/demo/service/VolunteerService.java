package com.example.demo.service;

import com.example.demo.entity.UserInfo;
import com.example.demo.entity.Volunteer;
import com.example.demo.repositories.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerService {
    private final UserInfoRepository userInfoRepository;

    public VolunteerService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public List<Volunteer> getAllVolunteers() {
        return userInfoRepository.findAll().stream().map(user -> {
            Volunteer volunteer = new Volunteer();
            volunteer.setName(user.getName());
            volunteer.setSkills(split(user.getSkills()));
            volunteer.setPreferences(split(user.getPreferences()));
            volunteer.setLocation(user.getCity());
            volunteer.setAvailability(user.getAvailability());
            return volunteer;
        }).collect(Collectors.toList());
    }

    public Volunteer addVolunteer(Volunteer volunteer) {
        UserInfo user = new UserInfo();
        user.setName(volunteer.getName());
        user.setSkills(String.join(",", volunteer.getSkills()));
        user.setPreferences(String.join(",", volunteer.getPreferences()));
        user.setCity(volunteer.getLocation());
        user.setAvailability(volunteer.getAvailability());

        userInfoRepository.save(user);
        return volunteer;
    }

    private List<String> split(String csv) {
        if (csv == null || csv.isEmpty()) return List.of();
        return Arrays.stream(csv.split(","))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }
}
