package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class EventTest {

    private Event createBaseEvent() {
        Event event = new Event();
        event.setId(1);
        event.setName("Test Event");
        event.setDescription("This is a test event description.");
        event.setLocation("Test Location");
        event.setDate(Timestamp.valueOf("2025-04-15 10:00:00"));
        event.setRequiredSkills("Skill1, Skill2");
        event.setUrgency("High");
        return event;
    }

    @Test
    public void testNoArgsConstructor() {
        Event event = new Event();
        assertNull(event.getId());
        assertNull(event.getName());
        assertNull(event.getDescription());
        assertNull(event.getLocation());
        assertNull(event.getDate());
        assertNull(event.getRequiredSkills());
        assertNull(event.getUrgency());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        Event event1 = new Event();
        Event event2 = new Event();
        
        // Test equality with all null fields
        assertEquals(event1, event2);
        assertEquals(event1.hashCode(), event2.hashCode());
        
        // Test with one field set to null and other to value
        event1.setId(1);
        event2.setId(null);
        assertNotEquals(event1, event2);
        
        event1 = new Event();
        event2 = new Event();
        event1.setName("Test Event");
        event2.setName(null);
        assertNotEquals(event1, event2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        Event event = createBaseEvent();
        assertNotEquals(event, new Object());
        assertNotEquals(event, null);
        assertNotEquals(event, "not an Event");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        Event event = createBaseEvent();
        assertEquals(event, event);
        assertEquals(event.hashCode(), event.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        Event event1 = createBaseEvent();
        Event event2 = createBaseEvent();

        // Test each field individually
        event2.setId(2);
        assertNotEquals(event1, event2);
        assertNotEquals(event1.hashCode(), event2.hashCode());

        event2 = createBaseEvent();
        event2.setName("Different Event");
        assertNotEquals(event1, event2);

        event2 = createBaseEvent();
        event2.setDescription("Different Description");
        assertNotEquals(event1, event2);

        event2 = createBaseEvent();
        event2.setLocation("Different Location");
        assertNotEquals(event1, event2);

        event2 = createBaseEvent();
        event2.setDate(Timestamp.valueOf("2025-04-16 11:00:00"));
        assertNotEquals(event1, event2);

        event2 = createBaseEvent();
        event2.setRequiredSkills("Different Skills");
        assertNotEquals(event1, event2);

        event2 = createBaseEvent();
        event2.setUrgency("Low");
        assertNotEquals(event1, event2);
    }

    @Test
    public void testToStringWithNullValues() {
        Event event = new Event();
        String eventString = event.toString();
        assertNotNull(eventString);
        assertTrue(eventString.contains("id=null"));
        assertTrue(eventString.contains("name=null"));
        assertTrue(eventString.contains("description=null"));
        assertTrue(eventString.contains("location=null"));
        assertTrue(eventString.contains("date=null"));
        assertTrue(eventString.contains("requiredSkills=null"));
        assertTrue(eventString.contains("urgency=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        Event event = createBaseEvent();
        String eventString = event.toString();

        assertNotNull(eventString);
        assertTrue(eventString.contains("id=1"));
        assertTrue(eventString.contains("name=Test Event"));
        assertTrue(eventString.contains("description=This is a test event description."));
        assertTrue(eventString.contains("location=Test Location"));
        assertTrue(eventString.contains("date=2025-04-15 10:00:00"));
        assertTrue(eventString.contains("requiredSkills=Skill1, Skill2"));
        assertTrue(eventString.contains("urgency=High"));
    }

    @Test
    public void testCanEqual() {
        Event event1 = createBaseEvent();
        Event event2 = new Event();
        
        // Test canEqual with same type
        assertTrue(event1.canEqual(event2));
        
        // Test canEqual with different type
        assertFalse(event1.canEqual(new Object()));
        assertFalse(event1.canEqual(null));
    }

    @Test
    public void testSettersAndGetters() {
        Event event = new Event();
        
        // Test each setter and getter with null values
        event.setId(null);
        assertNull(event.getId());

        event.setName(null);
        assertNull(event.getName());

        event.setDescription(null);
        assertNull(event.getDescription());

        event.setLocation(null);
        assertNull(event.getLocation());

        event.setDate(null);
        assertNull(event.getDate());

        event.setRequiredSkills(null);
        assertNull(event.getRequiredSkills());

        event.setUrgency(null);
        assertNull(event.getUrgency());

        // Test each setter and getter with actual values
        event.setId(1);
        assertEquals(1, event.getId());

        event.setName("Test Event");
        assertEquals("Test Event", event.getName());

        event.setDescription("Test Description");
        assertEquals("Test Description", event.getDescription());

        event.setLocation("Test Location");
        assertEquals("Test Location", event.getLocation());

        Timestamp date = Timestamp.valueOf("2025-04-15 10:00:00");
        event.setDate(date);
        assertEquals(date, event.getDate());

        event.setRequiredSkills("Skill1, Skill2");
        assertEquals("Skill1, Skill2", event.getRequiredSkills());

        event.setUrgency("High");
        assertEquals("High", event.getUrgency());
    }

    @Test
    public void testEdgeCases() {
        Event event = new Event();
        
        // Test with empty strings
        event.setName("");
        assertEquals("", event.getName());
        
        event.setDescription("");
        assertEquals("", event.getDescription());
        
        event.setLocation("");
        assertEquals("", event.getLocation());
        
        event.setRequiredSkills("");
        assertEquals("", event.getRequiredSkills());
        
        event.setUrgency("");
        assertEquals("", event.getUrgency());
        
        // Test with special characters
        event.setName("Event's Name");
        assertEquals("Event's Name", event.getName());
        
        event.setDescription("Description with \"quotes\" and 'apostrophes'");
        assertEquals("Description with \"quotes\" and 'apostrophes'", event.getDescription());
        
        event.setLocation("Location with spaces and-hyphens");
        assertEquals("Location with spaces and-hyphens", event.getLocation());
        
        event.setRequiredSkills("Skill1, Skill2, Skill3");
        assertEquals("Skill1, Skill2, Skill3", event.getRequiredSkills());
        
        event.setUrgency("Urgent!");
        assertEquals("Urgent!", event.getUrgency());
    }

    @Test
    public void testDateEdgeCases() {
        Event event = new Event();
        
        // Test with different date formats
        Timestamp date1 = Timestamp.valueOf("2025-04-15 00:00:00");
        event.setDate(date1);
        assertEquals(date1, event.getDate());
        
        Timestamp date2 = Timestamp.valueOf("2025-04-15 23:59:59");
        event.setDate(date2);
        assertEquals(date2, event.getDate());
        
        Timestamp date3 = Timestamp.valueOf("2025-04-15 12:00:00");
        event.setDate(date3);
        assertEquals(date3, event.getDate());
    }
}
