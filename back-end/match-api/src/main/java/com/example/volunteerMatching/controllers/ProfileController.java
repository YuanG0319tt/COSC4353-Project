package com.example.volunteerMatching.controller;

import com.example.volunteerMatching.models.UserProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileController {

    // In-memory storage for demonstration only
    private static UserProfile currentProfile;


    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getProfile() {
        if (currentProfile == null) {
            // Return 404 (Not Found) if no profile is set yet
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(currentProfile);
    }


    @PostMapping("/profile")
    public ResponseEntity<String> saveProfile(@RequestBody UserProfile profile) {
        // In a real app, you’d persist to a DB instead of a static variable
        currentProfile = profile;
        return ResponseEntity.ok("Profile saved successfully!");
    }
}
