package com.example.volunteerMatching;

import com.example.volunteerMatching.models.LoginRequest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    public void testNoArgConstructorAndSetters() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("secret123");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("secret123", request.getPassword());
    }

    @Test
    public void testAllArgsConstructor() {
        LoginRequest request = new LoginRequest("user@test.com", "pass456");

        assertEquals("user@test.com", request.getEmail());
        assertEquals("pass456", request.getPassword());
    }
}