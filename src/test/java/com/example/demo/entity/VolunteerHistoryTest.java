package com.example.demo.entity;

import org.junit.jupiter.api.Test;

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
    void equals_and_hashCode() {
        VolunteerHistory a = createBase();
        VolunteerHistory b = createBase();

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        b.setUserId(99);
        assertNotEquals(a, b);
    }

    @Test
    void toString_containsAllFields() {
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
    void canEqual_returnsTrueForSameType() {
        VolunteerHistory vh = createBase();
        assertTrue(vh.canEqual(new VolunteerHistory()));
    }

    @Test
    void canEqual_returnsFalseForOtherType() {
        VolunteerHistory vh = createBase();
        assertFalse(vh.canEqual("not a volunteer"));
    }
}
