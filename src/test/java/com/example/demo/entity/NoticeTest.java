package com.example.demo.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

public class NoticeTest {

    @Test
    public void testSettersAndGetters() {
        Notice notice = new Notice();
        notice.setId(1);
        notice.setTitle("Test Title");
        notice.setMessage("Test Message");
        notice.setType("announcement");
        Timestamp timestamp = Timestamp.valueOf("2025-04-15 10:00:00");
        notice.setCreateTime(timestamp);

        assertEquals(1, notice.getId());
        assertEquals("Test Title", notice.getTitle());
        assertEquals("Test Message", notice.getMessage());
        assertEquals("announcement", notice.getType());
        assertEquals(timestamp, notice.getCreateTime());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two Notice instances with identical values.
        Notice notice1 = new Notice();
        notice1.setId(1);
        notice1.setTitle("Test Title");
        notice1.setMessage("Test Message");
        notice1.setType("announcement");
        Timestamp timestamp = Timestamp.valueOf("2025-04-15 10:00:00");
        notice1.setCreateTime(timestamp);

        Notice notice2 = new Notice();
        notice2.setId(1);
        notice2.setTitle("Test Title");
        notice2.setMessage("Test Message");
        notice2.setType("announcement");
        notice2.setCreateTime(timestamp);

        // Create a Notice with different values.
        Notice notice3 = new Notice();
        notice3.setId(2);
        notice3.setTitle("Different Title");
        notice3.setMessage("Different Message");
        notice3.setType("info");
        notice3.setCreateTime(Timestamp.valueOf("2025-04-16 10:00:00"));

        // Verify equality and hash codes for identical objects.
        assertTrue(notice1.equals(notice2));
        assertEquals(notice1.hashCode(), notice2.hashCode());

        // Verify inequality for a different object.
        assertFalse(notice1.equals(notice3));
        // Optionally, check that hashCodes differ (if calculated from the same fields).
        assertNotEquals(notice1.hashCode(), notice3.hashCode());

        // Additional checks
        assertFalse(notice1.equals(null));
        assertFalse(notice1.equals("Some String"));
    }

    @Test
    public void testToString() {
        Notice notice = new Notice();
        notice.setId(1);
        notice.setTitle("Test Title");
        notice.setMessage("Test Message");
        notice.setType("announcement");
        Timestamp timestamp = Timestamp.valueOf("2025-04-15 10:00:00");
        notice.setCreateTime(timestamp);

        String noticeString = notice.toString();
        assertNotNull(noticeString);
        // Verify that the toString output contains key information.
        assertTrue(noticeString.contains("Test Title"));
        assertTrue(noticeString.contains("Test Message"));
        assertTrue(noticeString.contains("announcement"));
        // Check that it contains a part of the timestamp (year, for example).
        assertTrue(noticeString.contains("2025"));
    }
}
