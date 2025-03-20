package com.example.volunteerMatching.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VolHistoryTest {
    
    @Test
    void testVolHistoryConstructor() {
        VolHistory volHistory = new VolHistory("Stuart", "me@mail.com", "8324597865", "Cleaning", "2025-09-25", 12, "Completed");

        assertEquals("Stuart", volHistory.getName());
        assertEquals("me@mail.com", volHistory.getEmail());
        assertEquals("8324597865", volHistory.getPhoneNumber());
        assertEquals("Cleaning", volHistory.getEventName());
        assertEquals("2025-09-25", volHistory.getEventDate());
        assertEquals(12, volHistory.getHoursVolunteered());
        assertEquals("Completed", volHistory.getStatus());
    }
}