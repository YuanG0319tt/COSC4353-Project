package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class VolunteerReportEntryTest {

    private VolunteerReportEntry createBase() {
        return new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            LocalDate.of(2024, 1, 1),
            "Volunteer",
            5
        );
    }

    @Test
    public void testNoArgsConstructor() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        assertNull(entry.getVolunteerName());
        assertNull(entry.getEventName());
        assertNull(entry.getParticipationDate());
        assertNull(entry.getRole());
        assertNull(entry.getHours());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        VolunteerReportEntry entry = new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            date,
            "Volunteer",
            5
        );
        
        assertEquals("John Doe", entry.getVolunteerName());
        assertEquals("Community Cleanup", entry.getEventName());
        assertEquals(date, entry.getParticipationDate());
        assertEquals("Volunteer", entry.getRole());
        assertEquals(5, entry.getHours());
    }

    @Test
    public void testSettersAndGetters() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        
        // Test with null values
        entry.setVolunteerName(null);
        assertNull(entry.getVolunteerName());

        entry.setEventName(null);
        assertNull(entry.getEventName());

        entry.setParticipationDate(null);
        assertNull(entry.getParticipationDate());

        entry.setRole(null);
        assertNull(entry.getRole());

        entry.setHours(null);
        assertNull(entry.getHours());

        // Test with actual values
        entry.setVolunteerName("John Doe");
        assertEquals("John Doe", entry.getVolunteerName());

        entry.setEventName("Community Cleanup");
        assertEquals("Community Cleanup", entry.getEventName());

        LocalDate date = LocalDate.of(2024, 1, 1);
        entry.setParticipationDate(date);
        assertEquals(date, entry.getParticipationDate());

        entry.setRole("Volunteer");
        assertEquals("Volunteer", entry.getRole());

        entry.setHours(5);
        assertEquals(5, entry.getHours());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        VolunteerReportEntry entry1 = new VolunteerReportEntry();
        VolunteerReportEntry entry2 = new VolunteerReportEntry();
        
        // Test equality with all null fields
        assertEquals(entry1, entry2);
        assertEquals(entry1.hashCode(), entry2.hashCode());
        
        // Test with one field set to null and other to value
        entry1.setVolunteerName("John Doe");
        entry2.setVolunteerName(null);
        assertNotEquals(entry1, entry2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        VolunteerReportEntry entry = createBase();
        assertNotEquals(entry, new Object());
        assertNotEquals(entry, null);
        assertNotEquals(entry, "not a VolunteerReportEntry");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        VolunteerReportEntry entry = createBase();
        assertEquals(entry, entry);
        assertEquals(entry.hashCode(), entry.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        VolunteerReportEntry entry1 = createBase();
        VolunteerReportEntry entry2 = createBase();

        // Test each field individually
        entry2.setVolunteerName("Jane Doe");
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setEventName("Different Event");
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setParticipationDate(LocalDate.of(2024, 1, 2));
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setRole("Organizer");
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setHours(6);
        assertNotEquals(entry1, entry2);
    }

    @Test
    public void testToStringWithNullValues() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        String entryString = entry.toString();
        assertNotNull(entryString);
        assertTrue(entryString.contains("volunteerName=null"));
        assertTrue(entryString.contains("eventName=null"));
        assertTrue(entryString.contains("participationDate=null"));
        assertTrue(entryString.contains("role=null"));
        assertTrue(entryString.contains("hours=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        VolunteerReportEntry entry = createBase();
        String entryString = entry.toString();
        assertNotNull(entryString);
        assertTrue(entryString.contains("volunteerName=John Doe"));
        assertTrue(entryString.contains("eventName=Community Cleanup"));
        assertTrue(entryString.contains("participationDate=2024-01-01"));
        assertTrue(entryString.contains("role=Volunteer"));
        assertTrue(entryString.contains("hours=5"));
    }

    @Test
    public void testEdgeCases() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        
        // Test with empty strings
        entry.setVolunteerName("");
        assertEquals("", entry.getVolunteerName());
        
        entry.setEventName("");
        assertEquals("", entry.getEventName());
        
        entry.setRole("");
        assertEquals("", entry.getRole());
        
        // Test with special characters
        entry.setVolunteerName("John O'Connor");
        assertEquals("John O'Connor", entry.getVolunteerName());
        
        entry.setEventName("Event's Name");
        assertEquals("Event's Name", entry.getEventName());
        
        entry.setRole("Role-With-Hyphen");
        assertEquals("Role-With-Hyphen", entry.getRole());
        
        // Test with minimum and maximum hours
        entry.setHours(0);
        assertEquals(0, entry.getHours());
        
        entry.setHours(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, entry.getHours());
    }

    @Test
    public void testDateEdgeCases() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        
        // Test with different date formats
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        entry.setParticipationDate(date1);
        assertEquals(date1, entry.getParticipationDate());
        
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        entry.setParticipationDate(date2);
        assertEquals(date2, entry.getParticipationDate());
        
        // Test with leap year date
        LocalDate leapDate = LocalDate.of(2024, 2, 29);
        entry.setParticipationDate(leapDate);
        assertEquals(leapDate, entry.getParticipationDate());
    }
} 