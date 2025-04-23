package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerHistoryTest {

    private VolunteerHistory createBase() {
        VolunteerHistory vh = new VolunteerHistory();
        vh.setId(1);
        vh.setUid(2);
        vh.setEventId(3);
        vh.setEventName("EventX");
        vh.setVolunteerName("VolY");
        Timestamp ts = new Timestamp(1000L);
        vh.setParticipationDate(ts);
        vh.setRole("Helper");
        vh.setHours(5);
        vh.setDescription("Desc");
        vh.setLocation("Loc");
        vh.setRequiredSkills("S1,S2");
        vh.setUrgency("High");
        vh.setParticipationStatus("Attended");
        vh.setCompletionStatus("Completed");
        return vh;
    }

    @Test
    void equals_reflexiveAndNullAndTypeChecks() {
        VolunteerHistory vh = createBase();
        // reflexive
        assertTrue(vh.equals(vh));

        // null
        assertFalse(vh.equals(null));

        // different type
        assertFalse(vh.equals("not a VolunteerHistory"));
    }

    @Test
    void equals_fieldMismatchAnd_hashCode() {
        VolunteerHistory a = createBase();
        VolunteerHistory b = createBase();

        // initially equal
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
        assertEquals(a.hashCode(), b.hashCode());

        // change one field
        b.setUid(999);
        assertFalse(a.equals(b));
    }

    @Test
    void toString_containsAllFields() {
        VolunteerHistory vh = createBase();
        String s = vh.toString();
        assertTrue(s.contains("id=1"));
        assertTrue(s.contains("uid=2"));
        assertTrue(s.contains("eventId=3"));
        assertTrue(s.contains("eventName=EventX"));
        assertTrue(s.contains("volunteerName=VolY"));
        assertTrue(s.contains("participationDate=")); // timestamp prints here
        assertTrue(s.contains("role=Helper"));
        assertTrue(s.contains("hours=5"));
        assertTrue(s.contains("description=Desc"));
        assertTrue(s.contains("location=Loc"));
        assertTrue(s.contains("requiredSkills=S1,S2"));
        assertTrue(s.contains("urgency=High"));
        assertTrue(s.contains("participationStatus=Attended"));
        assertTrue(s.contains("completionStatus=Completed"));
    }

    @Test
    void canEqual_directInvocation() throws Exception {
        VolunteerHistory vh = createBase();
        Method canEqual = VolunteerHistory.class
                .getDeclaredMethod("canEqual", Object.class);
        canEqual.setAccessible(true);

        // same class → true
        assertTrue((Boolean) canEqual.invoke(vh, new VolunteerHistory()));

        // other type → false
        assertFalse((Boolean) canEqual.invoke(vh, "foo"));
    }
}
