package com.example.volunteerMatching.models;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class LoginResponse {
    private String message;
    private boolean success;

    public LoginResponse() {}

    public LoginResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @RestController
    @RequestMapping("/api")
    public static class AuthController {

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody com.example.volunteerMatching.models.LoginRequest loginRequest) {
            // Simulated authentication logic (replace with actual authentication)
            if("user@example.com".equals(loginRequest.getEmail()) && "password".equals(loginRequest.getPassword())){
                return ResponseEntity.ok(new LoginResponse("Login successful", true));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse("Invalid email or password", false));
            }
        }
    }
}
