package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void testSettersAndGetters() {
        Event event = new Event();

        // Prepare test values
        Integer id = 1;
        String name = "Test Event";
        String description = "This is a test event description.";
        String location = "Test Location";
        Timestamp date = Timestamp.valueOf("2025-04-15 10:00:00");
        String requiredSkills = "Skill1, Skill2";
        String urgency = "High";

        // Set values
        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);
        event.setDate(date);
        event.setRequiredSkills(requiredSkills);
        event.setUrgency(urgency);

        // Verify values with getters
        assertEquals(id, event.getId());
        assertEquals(name, event.getName());
        assertEquals(description, event.getDescription());
        assertEquals(location, event.getLocation());
        assertEquals(date, event.getDate());
        assertEquals(requiredSkills, event.getRequiredSkills());
        assertEquals(urgency, event.getUrgency());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two Event objects with identical values
        Event event1 = new Event();
        event1.setId(1);
        event1.setName("Test Event");
        event1.setDescription("Test Desc");
        event1.setLocation("Location A");
        Timestamp ts = Timestamp.valueOf("2025-04-15 10:00:00");
        event1.setDate(ts);
        event1.setRequiredSkills("Skill1");
        event1.setUrgency("Medium");

        Event event2 = new Event();
        event2.setId(1);
        event2.setName("Test Event");
        event2.setDescription("Test Desc");
        event2.setLocation("Location A");
        event2.setDate(ts);
        event2.setRequiredSkills("Skill1");
        event2.setUrgency("Medium");

        // Create an Event with different values
        Event event3 = new Event();
        event3.setId(2);
        event3.setName("Different Event");
        event3.setDescription("Different Desc");
        event3.setLocation("Location B");
        Timestamp ts2 = Timestamp.valueOf("2025-04-16 11:00:00");
        event3.setDate(ts2);
        event3.setRequiredSkills("Skill2");
        event3.setUrgency("Low");

        // Verify equality and hash codes for identical objects
        assertTrue(event1.equals(event2));
        assertEquals(event1.hashCode(), event2.hashCode());

        // Verify inequality for different objects
        assertFalse(event1.equals(event3));
        assertNotEquals(event1.hashCode(), event3.hashCode());

        // Check equals with null and different type
        assertFalse(event1.equals(null));
        assertFalse(event1.equals("Some String"));
    }

    @Test
    public void testToString() {
        Event event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("Test Description");
        event.setLocation("Test Location");
        Timestamp date = Timestamp.valueOf("2025-04-15 10:00:00");
        event.setDate(date);
        event.setRequiredSkills("Skill1, Skill2");
        event.setUrgency("High");

        String eventString = event.toString();
        assertNotNull(eventString);
        // Check that key information is present in the toString output
        assertTrue(eventString.contains("Test Event"));
        assertTrue(eventString.contains("Test Description"));
        assertTrue(eventString.contains("Test Location"));
        assertTrue(eventString.contains("Skill1, Skill2"));
        assertTrue(eventString.contains("High"));
    }
}
