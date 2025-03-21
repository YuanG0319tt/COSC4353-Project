package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.LoginRequest;
import com.example.volunteerMatching.models.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Simulated authentication logic; replace with your actual authentication mechanism
        if ("user@example.com".equals(loginRequest.getEmail()) &&
                "password".equals(loginRequest.getPassword())) {
            return ResponseEntity.ok(new LoginResponse("Login successful", true));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse("Invalid email or password", false));
        }
    }
}
