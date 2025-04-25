package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class EventReportEntryTest {

    private EventReportEntry createBase() {
        return new EventReportEntry(
            "Community Cleanup",
            LocalDate.of(2024, 1, 1),
            "John Doe",
            "Volunteer"
        );
    }

    @Test
    public void testNoArgsConstructor() {
        EventReportEntry entry = new EventReportEntry();
        assertNull(entry.getEventName());
        assertNull(entry.getEventDate());
        assertNull(entry.getVolunteerName());
        assertNull(entry.getRole());
    }

    @Test
    public void testAllArgsConstructor() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        EventReportEntry entry = new EventReportEntry(
            "Community Cleanup",
            date,
            "John Doe",
            "Volunteer"
        );
        
        assertEquals("Community Cleanup", entry.getEventName());
        assertEquals(date, entry.getEventDate());
        assertEquals("John Doe", entry.getVolunteerName());
        assertEquals("Volunteer", entry.getRole());
    }

    @Test
    public void testSettersAndGetters() {
        EventReportEntry entry = new EventReportEntry();
        
        // Test with null values
        entry.setEventName(null);
        assertNull(entry.getEventName());

        entry.setEventDate(null);
        assertNull(entry.getEventDate());

        entry.setVolunteerName(null);
        assertNull(entry.getVolunteerName());

        entry.setRole(null);
        assertNull(entry.getRole());

        // Test with actual values
        entry.setEventName("Community Cleanup");
        assertEquals("Community Cleanup", entry.getEventName());

        LocalDate date = LocalDate.of(2024, 1, 1);
        entry.setEventDate(date);
        assertEquals(date, entry.getEventDate());

        entry.setVolunteerName("John Doe");
        assertEquals("John Doe", entry.getVolunteerName());

        entry.setRole("Volunteer");
        assertEquals("Volunteer", entry.getRole());
    }

    @Test
    public void testEqualsAndHashCodeWithNullFields() {
        EventReportEntry entry1 = new EventReportEntry();
        EventReportEntry entry2 = new EventReportEntry();
        
        // Test equality with all null fields
        assertEquals(entry1, entry2);
        assertEquals(entry1.hashCode(), entry2.hashCode());
        
        // Test with one field set to null and other to value
        entry1.setEventName("Community Cleanup");
        entry2.setEventName(null);
        assertNotEquals(entry1, entry2);
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentTypes() {
        EventReportEntry entry = createBase();
        assertNotEquals(entry, new Object());
        assertNotEquals(entry, null);
        assertNotEquals(entry, "not an EventReportEntry");
    }

    @Test
    public void testEqualsAndHashCodeWithSameObject() {
        EventReportEntry entry = createBase();
        assertEquals(entry, entry);
        assertEquals(entry.hashCode(), entry.hashCode());
    }

    @Test
    public void testEqualsAndHashCodeWithDifferentValues() {
        EventReportEntry entry1 = createBase();
        EventReportEntry entry2 = createBase();

        // Test each field individually
        entry2.setEventName("Different Event");
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setEventDate(LocalDate.of(2024, 1, 2));
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setVolunteerName("Jane Doe");
        assertNotEquals(entry1, entry2);

        entry2 = createBase();
        entry2.setRole("Organizer");
        assertNotEquals(entry1, entry2);
    }

    @Test
    public void testToStringWithNullValues() {
        EventReportEntry entry = new EventReportEntry();
        String entryString = entry.toString();
        assertNotNull(entryString);
        assertTrue(entryString.contains("eventName=null"));
        assertTrue(entryString.contains("eventDate=null"));
        assertTrue(entryString.contains("volunteerName=null"));
        assertTrue(entryString.contains("role=null"));
    }

    @Test
    public void testToStringWithAllValues() {
        EventReportEntry entry = createBase();
        String entryString = entry.toString();
        assertNotNull(entryString);
        assertTrue(entryString.contains("eventName=Community Cleanup"));
        assertTrue(entryString.contains("eventDate=2024-01-01"));
        assertTrue(entryString.contains("volunteerName=John Doe"));
        assertTrue(entryString.contains("role=Volunteer"));
    }

    @Test
    public void testEdgeCases() {
        EventReportEntry entry = new EventReportEntry();
        
        // Test with empty strings
        entry.setEventName("");
        assertEquals("", entry.getEventName());
        
        entry.setVolunteerName("");
        assertEquals("", entry.getVolunteerName());
        
        entry.setRole("");
        assertEquals("", entry.getRole());
        
        // Test with special characters
        entry.setEventName("Event's Name");
        assertEquals("Event's Name", entry.getEventName());
        
        entry.setVolunteerName("John O'Connor");
        assertEquals("John O'Connor", entry.getVolunteerName());
        
        entry.setRole("Role-With-Hyphen");
        assertEquals("Role-With-Hyphen", entry.getRole());
    }

    @Test
    public void testDateEdgeCases() {
        EventReportEntry entry = new EventReportEntry();
        
        // Test with different date formats
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        entry.setEventDate(date1);
        assertEquals(date1, entry.getEventDate());
        
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        entry.setEventDate(date2);
        assertEquals(date2, entry.getEventDate());
        
        // Test with leap year date
        LocalDate leapDate = LocalDate.of(2024, 2, 29);
        entry.setEventDate(leapDate);
        assertEquals(leapDate, entry.getEventDate());
    }
} 