package com.example.volunteerMatching.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

public class EventTest {

    @Test
    void testEventConstructor() {
        List<String> result = Arrays.asList("Lifting", "Organizing");
        Event event = new Event("Houston", "Cleaning", "2025-03-20", result, "High", "Picking up garbage.");

        assertEquals("Houston", event.getLocation());
        assertEquals("Cleaning", event.getName());
        assertEquals("2025-03-20", event.getDate());
        assertIterableEquals(result, event.getRequirements());
        assertEquals("High", event.getUrgency());
        assertEquals("Picking up garbage.", event.getDescription());
    }
}