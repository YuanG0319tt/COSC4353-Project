package com.example.demo.entity.DTO;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ReportEntryDTOTest {

    @Test
    void testVolunteerReportEntryConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        VolunteerReportEntry entry = new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            date,
            "Cleanup Crew",
            4
        );

        assertEquals("John Doe", entry.getVolunteerName());
        assertEquals("Community Cleanup", entry.getEventName());
        assertEquals(date, entry.getParticipationDate());
        assertEquals("Cleanup Crew", entry.getRole());
        assertEquals(4, entry.getHours());
    }

    @Test
    void testVolunteerReportEntrySetters() {
        VolunteerReportEntry entry = new VolunteerReportEntry();
        LocalDate date = LocalDate.now();

        entry.setVolunteerName("Jane Smith");
        entry.setEventName("Food Drive");
        entry.setParticipationDate(date);
        entry.setRole("Distribution");
        entry.setHours(6);

        assertEquals("Jane Smith", entry.getVolunteerName());
        assertEquals("Food Drive", entry.getEventName());
        assertEquals(date, entry.getParticipationDate());
        assertEquals("Distribution", entry.getRole());
        assertEquals(6, entry.getHours());
    }

    @Test
    void testVolunteerReportEntryEqualsAndHashCode() {
        LocalDate date = LocalDate.now();
        VolunteerReportEntry entry1 = new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            date,
            "Cleanup Crew",
            4
        );
        VolunteerReportEntry entry2 = new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            date,
            "Cleanup Crew",
            4
        );
        VolunteerReportEntry entry3 = new VolunteerReportEntry(
            "Jane Smith",
            "Food Drive",
            date,
            "Distribution",
            6
        );

        assertEquals(entry1, entry2);
        assertNotEquals(entry1, entry3);
        assertEquals(entry1.hashCode(), entry2.hashCode());
        assertNotEquals(entry1.hashCode(), entry3.hashCode());
    }

    @Test
    void testVolunteerReportEntryToString() {
        LocalDate date = LocalDate.now();
        VolunteerReportEntry entry = new VolunteerReportEntry(
            "John Doe",
            "Community Cleanup",
            date,
            "Cleanup Crew",
            4
        );

        String expected = "VolunteerReportEntry(volunteerName=John Doe, eventName=Community Cleanup, " +
                         "participationDate=" + date + ", role=Cleanup Crew, hours=4)";
        assertEquals(expected, entry.toString());
    }

    @Test
    void testEventReportEntryConstructorAndGetters() {
        LocalDate date = LocalDate.now();
        EventReportEntry entry = new EventReportEntry(
            "Community Cleanup",
            date,
            "John Doe",
            "Cleanup Crew"
        );

        assertEquals("Community Cleanup", entry.getEventName());
        assertEquals(date, entry.getEventDate());
        assertEquals("John Doe", entry.getVolunteerName());
        assertEquals("Cleanup Crew", entry.getRole());
    }

    @Test
    void testEventReportEntrySetters() {
        EventReportEntry entry = new EventReportEntry();
        LocalDate date = LocalDate.now();

        entry.setEventName("Food Drive");
        entry.setEventDate(date);
        entry.setVolunteerName("Jane Smith");
        entry.setRole("Distribution");

        assertEquals("Food Drive", entry.getEventName());
        assertEquals(date, entry.getEventDate());
        assertEquals("Jane Smith", entry.getVolunteerName());
        assertEquals("Distribution", entry.getRole());
    }

    @Test
    void testEventReportEntryEqualsAndHashCode() {
        LocalDate date = LocalDate.now();
        EventReportEntry entry1 = new EventReportEntry(
            "Community Cleanup",
            date,
            "John Doe",
            "Cleanup Crew"
        );
        EventReportEntry entry2 = new EventReportEntry(
            "Community Cleanup",
            date,
            "John Doe",
            "Cleanup Crew"
        );
        EventReportEntry entry3 = new EventReportEntry(
            "Food Drive",
            date,
            "Jane Smith",
            "Distribution"
        );

        assertEquals(entry1, entry2);
        assertNotEquals(entry1, entry3);
        assertEquals(entry1.hashCode(), entry2.hashCode());
        assertNotEquals(entry1.hashCode(), entry3.hashCode());
    }

    @Test
    void testEventReportEntryToString() {
        LocalDate date = LocalDate.now();
        EventReportEntry entry = new EventReportEntry(
            "Community Cleanup",
            date,
            "John Doe",
            "Cleanup Crew"
        );

        String expected = "EventReportEntry(eventName=Community Cleanup, eventDate=" + date + 
                         ", volunteerName=John Doe, role=Cleanup Crew)";
        assertEquals(expected, entry.toString());
    }

    @Test
    void testNullValues() {
        VolunteerReportEntry volunteerEntry = new VolunteerReportEntry();
        EventReportEntry eventEntry = new EventReportEntry();

        assertNull(volunteerEntry.getVolunteerName());
        assertNull(volunteerEntry.getEventName());
        assertNull(volunteerEntry.getParticipationDate());
        assertNull(volunteerEntry.getRole());
        assertNull(volunteerEntry.getHours());

        assertNull(eventEntry.getEventName());
        assertNull(eventEntry.getEventDate());
        assertNull(eventEntry.getVolunteerName());
        assertNull(eventEntry.getRole());
    }
} 