package com.example.volunteerMatching;

import org.junit.jupiter.api.Test;
import com.example.volunteerMatching.models.Notification;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class NotificationTest {

    @Test
    void testNotificationConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification("Alert", "System Maintenance", "announcement", now);

        assertEquals("Alert", notification.getTitle());
        assertEquals("System Maintenance", notification.getMessage());
        assertEquals("announcement", notification.getType());
        assertEquals(now.withNano(0), notification.getTimestamp().withNano(0));
    }

    @Test
    void testDefaultTimestamp() {
        Notification notification = new Notification();
        assertNotNull(notification.getTimestamp());
    }
}
