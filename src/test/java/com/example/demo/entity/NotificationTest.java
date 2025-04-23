package com.example.demo.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void defaultConstructor_setsTimestampToNow() {
        LocalDateTime before = LocalDateTime.now();
        Notification n = new Notification();
        LocalDateTime after = LocalDateTime.now();

        assertNull(n.getId(),           "id should be null before persistence");
        assertNotNull(n.getTimestamp(), "timestamp should be initialized");
        // timestamp ∈ [before, after]
        assertFalse(n.getTimestamp().isBefore(before),
                () -> "timestamp " + n.getTimestamp() + " is before " + before);
        assertFalse(n.getTimestamp().isAfter(after),
                () -> "timestamp " + n.getTimestamp() + " is after " + after);
    }

    @Test
    void parameterizedConstructor_withNonNullTimestamp() {
        LocalDateTime ts = LocalDateTime.of(2021, 12, 31, 23, 59);
        Notification n = new Notification("MyTitle", "MyMessage", "announcement", ts);

        assertNull(n.getId(), "id still null");
        assertEquals("MyTitle",   n.getTitle());
        assertEquals("MyMessage", n.getMessage());
        assertEquals("announcement", n.getType());
        assertSame(ts, n.getTimestamp(),
                "when supplied non‑null timestamp, it must be used directly");
    }

    @Test
    void parameterizedConstructor_withNullTimestamp_defaultsToNow() {
        LocalDateTime before = LocalDateTime.now();
        Notification n = new Notification("T", "M", "notification", null);
        LocalDateTime after = LocalDateTime.now();

        assertNull(n.getId());
        // timestamp should be between before..after
        LocalDateTime ts = n.getTimestamp();
        assertNotNull(ts);
        assertFalse(ts.isBefore(before),
                () -> "timestamp " + ts + " is before " + before);
        assertFalse(ts.isAfter(after),
                () -> "timestamp " + ts + " is after " + after);
    }

    @Test
    void gettersAndSetters_coverAllFields() {
        Notification n = new Notification();

        // id has no setter; still null
        assertNull(n.getId());

        // title
        n.setTitle("Hello");
        assertEquals("Hello", n.getTitle());

        // message
        n.setMessage("World");
        assertEquals("World", n.getMessage());

        // type
        n.setType("announcement");
        assertEquals("announcement", n.getType());

        // timestamp
        LocalDateTime custom = LocalDateTime.of(2000, 1, 1, 0, 0);
        n.setTimestamp(custom);
        assertEquals(custom, n.getTimestamp());
    }
}
