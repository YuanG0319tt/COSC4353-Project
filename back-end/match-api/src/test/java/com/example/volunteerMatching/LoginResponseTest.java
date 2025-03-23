package com.example.volunteerMatching;

import com.example.volunteerMatching.models.LoginResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginResponseTest {

    @Test
    public void testGettersAndSetters() {
        LoginResponse response = new LoginResponse();
        response.setMessage("Test message");
        response.setSuccess(true);

        assertEquals("Test message", response.getMessage());
        assertTrue(response.isSuccess());
    }

    @Test
    public void testConstructor() {
        LoginResponse response = new LoginResponse("Welcome", false);
        assertEquals("Welcome", response.getMessage());
        assertFalse(response.isSuccess());
    }
}
