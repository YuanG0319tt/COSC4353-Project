package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class VolunteerHistoryJTest {

    private VolunteerHistoryJ createBase() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        history.setHistoryId(1);
        history.setUid(100);
        history.setEventId(200);
        history.setParticipationDate(LocalDate.of(2024, 1, 1));
        history.setHoursVolunteered(5);
        history.setParticipationStatus("Completed");
        return history;
    }

    @Test
    public void testNoArgsConstructor() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        assertNull(history.getHistoryId());
        assertNull(history.getUid());
        assertNull(history.getEventId());
        assertNull(history.getParticipationDate());
        assertNull(history.getHoursVolunteered());
        assertNull(history.getParticipationStatus());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        VolunteerHistoryJ history = new VolunteerHistoryJ(100, 200, date, 5, "Completed");
        
        assertEquals(100, history.getUid());
        assertEquals(200, history.getEventId());
        assertEquals(date, history.getParticipationDate());
        assertEquals(5, history.getHoursVolunteered());
        assertEquals("Completed", history.getParticipationStatus());
    }

    @Test
    public void testSettersAndGetters() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        
        // Test with null values
        history.setHistoryId(null);
        assertNull(history.getHistoryId());

        history.setUid(null);
        assertNull(history.getUid());

        history.setEventId(null);
        assertNull(history.getEventId());

        history.setParticipationDate(null);
        assertNull(history.getParticipationDate());

        history.setHoursVolunteered(null);
        assertNull(history.getHoursVolunteered());

        history.setParticipationStatus(null);
        assertNull(history.getParticipationStatus());

        // Test with actual values
        history.setHistoryId(1);
        assertEquals(1, history.getHistoryId());

        history.setUid(100);
        assertEquals(100, history.getUid());

        history.setEventId(200);
        assertEquals(200, history.getEventId());

        LocalDate date = LocalDate.of(2024, 1, 1);
        history.setParticipationDate(date);
        assertEquals(date, history.getParticipationDate());

        history.setHoursVolunteered(5);
        assertEquals(5, history.getHoursVolunteered());

        history.setParticipationStatus("Completed");
        assertEquals("Completed", history.getParticipationStatus());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        VolunteerHistoryJ history1 = new VolunteerHistoryJ();
        VolunteerHistoryJ history2 = new VolunteerHistoryJ();
        
        // Test equality with all null fields
        assertNotEquals(history1, history2); // Different objects with same null values are not equal
        assertNotEquals(history1.hashCode(), history2.hashCode());
        
        // Test with one field set to null and other to value
        history1.setHistoryId(1);
        history2.setHistoryId(null);
        assertNotEquals(history1, history2);
        
        history1 = new VolunteerHistoryJ();
        history2 = new VolunteerHistoryJ();
        history1.setUid(100);
        history2.setUid(null);
        assertNotEquals(history1, history2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        VolunteerHistoryJ history = createBase();
        assertNotEquals(history, new Object());
        assertNotEquals(history, null);
        assertNotEquals(history, "not a VolunteerHistoryJ");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        VolunteerHistoryJ history = createBase();
        assertEquals(history, history);
        assertEquals(history.hashCode(), history.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        VolunteerHistoryJ history1 = createBase();
        VolunteerHistoryJ history2 = createBase();

        // Test each field individually
        history2.setHistoryId(2);
        assertNotEquals(history1, history2);
        assertNotEquals(history1.hashCode(), history2.hashCode());

        history2 = createBase();
        history2.setUid(101);
        assertNotEquals(history1, history2);

        history2 = createBase();
        history2.setEventId(201);
        assertNotEquals(history1, history2);

        history2 = createBase();
        history2.setParticipationDate(LocalDate.of(2024, 1, 2));
        assertNotEquals(history1, history2);

        history2 = createBase();
        history2.setHoursVolunteered(6);
        assertNotEquals(history1, history2);

        history2 = createBase();
        history2.setParticipationStatus("Pending");
        assertNotEquals(history1, history2);
    }

    @Test
    public void testToStringWithNullValues() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        String historyString = history.toString();
        assertNotNull(historyString);
        // Since toString() is not overridden, we can't make assumptions about its format
        // Just verify it's not null and contains the class name
        assertTrue(historyString.contains("VolunteerHistoryJ"));
    }

    @Test
    public void testToStringWithAllValues() {
        VolunteerHistoryJ history = createBase();
        String historyString = history.toString();
        assertNotNull(historyString);
        // Since toString() is not overridden, we can't make assumptions about its format
        // Just verify it's not null and contains the class name
        assertTrue(historyString.contains("VolunteerHistoryJ"));
    }

    @Test
    public void testEdgeCases() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        
        // Test with minimum values
        history.setHistoryId(0);
        assertEquals(0, history.getHistoryId());
        
        history.setUid(0);
        assertEquals(0, history.getUid());
        
        history.setEventId(0);
        assertEquals(0, history.getEventId());
        
        history.setHoursVolunteered(0);
        assertEquals(0, history.getHoursVolunteered());
        
        // Test with maximum values
        history.setHistoryId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, history.getHistoryId());
        
        history.setUid(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, history.getUid());
        
        history.setEventId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, history.getEventId());
        
        history.setHoursVolunteered(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, history.getHoursVolunteered());
        
        // Test with empty string
        history.setParticipationStatus("");
        assertEquals("", history.getParticipationStatus());
        
        // Test with special characters
        history.setParticipationStatus("In-Progress");
        assertEquals("In-Progress", history.getParticipationStatus());
    }

    @Test
    public void testDateEdgeCases() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        
        // Test with different date formats
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        history.setParticipationDate(date1);
        assertEquals(date1, history.getParticipationDate());
        
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        history.setParticipationDate(date2);
        assertEquals(date2, history.getParticipationDate());
        
        // Test with leap year date
        LocalDate leapDate = LocalDate.of(2024, 2, 29);
        history.setParticipationDate(leapDate);
        assertEquals(leapDate, history.getParticipationDate());
    }

    @Test
    public void testStatusEdgeCases() {
        VolunteerHistoryJ history = new VolunteerHistoryJ();
        
        // Test various status formats
        history.setParticipationStatus("Completed");
        assertEquals("Completed", history.getParticipationStatus());
        
        history.setParticipationStatus("In Progress");
        assertEquals("In Progress", history.getParticipationStatus());
        
        history.setParticipationStatus("Pending");
        assertEquals("Pending", history.getParticipationStatus());
        
        history.setParticipationStatus("Cancelled");
        assertEquals("Cancelled", history.getParticipationStatus());
        
        // Test with special characters
        history.setParticipationStatus("On-Hold");
        assertEquals("On-Hold", history.getParticipationStatus());
        
        history.setParticipationStatus("In-Progress");
        assertEquals("In-Progress", history.getParticipationStatus());
    }
} 