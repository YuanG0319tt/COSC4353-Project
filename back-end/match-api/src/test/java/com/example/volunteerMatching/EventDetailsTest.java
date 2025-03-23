package com.example.volunteerMatching;

import org.junit.jupiter.api.Test;
import com.example.volunteerMatching.models.EventDetails;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class EventDetailsTest {

    @Test
    void testEventDetailsSettersAndGetters() {
        EventDetails event = new EventDetails();
        event.setLocation("Houston");
        event.setEventName("Cleaning");
        event.setEventDate(LocalDate.of(2025, 3, 20));
        event.setRequiredSkills("Lifting,Organizing");
        event.setUrgency(5);
        event.setDescription("Picking up garbage.");

        assertEquals("Houston", event.getLocation());
        assertEquals("Cleaning", event.getEventName());
        assertEquals(LocalDate.of(2025, 3, 20), event.getEventDate());
        assertEquals("Lifting,Organizing", event.getRequiredSkills());
        assertEquals(5, event.getUrgency());
        assertEquals("Picking up garbage.", event.getDescription());
    }
}