package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerHistoryTest {

    private VolunteerHistory createBase() {
        VolunteerHistory vh = new VolunteerHistory();
        vh.setHistoryId(1L);
        vh.setUserId(2);
        vh.setEventId(3);
        vh.setParticipationDate(Date.valueOf("2025-05-19"));
        vh.setHoursVolunteered(4);
        vh.setEmail("volunteer@example.com");
        vh.setEventDate("2025-05-20");
        vh.setEventName("Community Clean-Up");
        vh.setName("Jordan Reyes");
        vh.setPhoneNumber("555-123-4567");
        vh.setStatus("Completed");
        return vh;
    }

    @Test
    void testNoArgsConstructor() {
        VolunteerHistory vh = new VolunteerHistory();
        assertNull(vh.getHistoryId());
        assertNull(vh.getUserId());
        assertNull(vh.getEventId());
        assertNull(vh.getParticipationDate());
        assertNull(vh.getHoursVolunteered());
        assertNull(vh.getEmail());
        assertNull(vh.getEventDate());
        assertNull(vh.getEventName());
        assertNull(vh.getName());
        assertNull(vh.getPhoneNumber());
        assertNull(vh.getStatus());
    }

    @Test
    void testEqualsAndHashCodeWithNullFields() {
        VolunteerHistory vh1 = new VolunteerHistory();
        VolunteerHistory vh2 = new VolunteerHistory();
        
        // Test equality with all null fields
        assertEquals(vh1, vh2);
        assertEquals(vh1.hashCode(), vh2.hashCode());
        
        // Test with one field set to null and other to value
        vh1.setHistoryId(1L);
        vh2.setHistoryId(null);
        assertNotEquals(vh1, vh2);
        
        vh1 = new VolunteerHistory();
        vh2 = new VolunteerHistory();
        vh1.setUserId(1);
        vh2.setUserId(null);
        assertNotEquals(vh1, vh2);
    }

    @Test
    void testEqualsAndHashCodeWithDifferentTypes() {
        VolunteerHistory vh = createBase();
        assertNotEquals(vh, new Object());
        assertNotEquals(vh, null);
        assertNotEquals(vh, "not a VolunteerHistory");
    }

    @Test
    void testEqualsAndHashCodeWithSameObject() {
        VolunteerHistory vh = createBase();
        assertEquals(vh, vh);
        assertEquals(vh.hashCode(), vh.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithDifferentValues() {
        VolunteerHistory a = createBase();
        VolunteerHistory b = createBase();

        // Test each field individually
        b.setHistoryId(99L);
        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());

        b = createBase();
        b.setUserId(99);
        assertNotEquals(a, b);

        b = createBase();
        b.setEventId(99);
        assertNotEquals(a, b);

        b = createBase();
        b.setParticipationDate(Date.valueOf("2025-05-20"));
        assertNotEquals(a, b);

        b = createBase();
        b.setHoursVolunteered(99);
        assertNotEquals(a, b);

        b = createBase();
        b.setEmail("different@example.com");
        assertNotEquals(a, b);

        b = createBase();
        b.setEventDate("2025-05-21");
        assertNotEquals(a, b);

        b = createBase();
        b.setEventName("Different Event");
        assertNotEquals(a, b);

        b = createBase();
        b.setName("Different Name");
        assertNotEquals(a, b);

        b = createBase();
        b.setPhoneNumber("555-987-6543");
        assertNotEquals(a, b);

        b = createBase();
        b.setStatus("Pending");
        assertNotEquals(a, b);
    }

    @Test
    void testToStringWithNullValues() {
        VolunteerHistory vh = new VolunteerHistory();
        String s = vh.toString();
        assertNotNull(s);
        assertTrue(s.contains("historyId=null"));
        assertTrue(s.contains("userId=null"));
        assertTrue(s.contains("eventId=null"));
        assertTrue(s.contains("participationDate=null"));
        assertTrue(s.contains("hoursVolunteered=null"));
        assertTrue(s.contains("email=null"));
        assertTrue(s.contains("eventDate=null"));
        assertTrue(s.contains("eventName=null"));
        assertTrue(s.contains("name=null"));
        assertTrue(s.contains("phoneNumber=null"));
        assertTrue(s.contains("status=null"));
    }

    @Test
    void testToStringWithAllValues() {
        VolunteerHistory vh = createBase();
        String s = vh.toString();

        assertTrue(s.contains("historyId=1"));
        assertTrue(s.contains("userId=2"));
        assertTrue(s.contains("eventId=3"));
        assertTrue(s.contains("participationDate=2025-05-19"));
        assertTrue(s.contains("hoursVolunteered=4"));
        assertTrue(s.contains("email=volunteer@example.com"));
        assertTrue(s.contains("eventDate=2025-05-20"));
        assertTrue(s.contains("eventName=Community Clean-Up"));
        assertTrue(s.contains("name=Jordan Reyes"));
        assertTrue(s.contains("phoneNumber=555-123-4567"));
        assertTrue(s.contains("status=Completed"));
    }

    @Test
    void testCanEqual() {
        VolunteerHistory vh = createBase();
        assertTrue(vh.canEqual(new VolunteerHistory()));
        assertFalse(vh.canEqual(new Object()));
        assertFalse(vh.canEqual(null));
    }

    @Test
    void testSettersAndGetters() {
        VolunteerHistory vh = new VolunteerHistory();
        
        // Test each setter and getter with null values
        vh.setHistoryId(null);
        assertNull(vh.getHistoryId());

        vh.setUserId(null);
        assertNull(vh.getUserId());

        vh.setEventId(null);
        assertNull(vh.getEventId());

        vh.setParticipationDate(null);
        assertNull(vh.getParticipationDate());

        vh.setHoursVolunteered(null);
        assertNull(vh.getHoursVolunteered());

        vh.setEmail(null);
        assertNull(vh.getEmail());

        vh.setEventDate(null);
        assertNull(vh.getEventDate());

        vh.setEventName(null);
        assertNull(vh.getEventName());

        vh.setName(null);
        assertNull(vh.getName());

        vh.setPhoneNumber(null);
        assertNull(vh.getPhoneNumber());

        vh.setStatus(null);
        assertNull(vh.getStatus());

        // Test each setter and getter with actual values
        vh.setHistoryId(1L);
        assertEquals(1L, vh.getHistoryId());

        vh.setUserId(2);
        assertEquals(2, vh.getUserId());

        vh.setEventId(3);
        assertEquals(3, vh.getEventId());

        Date date = Date.valueOf("2025-05-19");
        vh.setParticipationDate(date);
        assertEquals(date, vh.getParticipationDate());

        vh.setHoursVolunteered(4);
        assertEquals(4, vh.getHoursVolunteered());

        vh.setEmail("test@example.com");
        assertEquals("test@example.com", vh.getEmail());

        vh.setEventDate("2025-05-20");
        assertEquals("2025-05-20", vh.getEventDate());

        vh.setEventName("Test Event");
        assertEquals("Test Event", vh.getEventName());

        vh.setName("Test Name");
        assertEquals("Test Name", vh.getName());

        vh.setPhoneNumber("555-123-4567");
        assertEquals("555-123-4567", vh.getPhoneNumber());

        vh.setStatus("Test Status");
        assertEquals("Test Status", vh.getStatus());
    }

    @Test
    void testEdgeCases() {
        VolunteerHistory vh = new VolunteerHistory();
        
        // Test with empty strings
        vh.setEmail("");
        assertEquals("", vh.getEmail());
        
        vh.setEventDate("");
        assertEquals("", vh.getEventDate());
        
        vh.setEventName("");
        assertEquals("", vh.getEventName());
        
        vh.setName("");
        assertEquals("", vh.getName());
        
        vh.setPhoneNumber("");
        assertEquals("", vh.getPhoneNumber());
        
        vh.setStatus("");
        assertEquals("", vh.getStatus());
        
        // Test with special characters
        vh.setEmail("test@example.com");
        assertEquals("test@example.com", vh.getEmail());
        
        vh.setPhoneNumber("+1 (555) 123-4567");
        assertEquals("+1 (555) 123-4567", vh.getPhoneNumber());
        
        vh.setName("John O'Connor");
        assertEquals("John O'Connor", vh.getName());
    }
}
