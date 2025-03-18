package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Notification;
import com.example.volunteerMatching.repositories.NotificationRepository;
import com.example.volunteerMatching.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllNotifications() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification1 = new Notification("Title1", "Message1", "Type1", now);
        Notification notification2 = new Notification("Title2", "Message2", "Type2", now);
        List<Notification> notifications = List.of(notification1, notification2);

        when(notificationRepository.findAll()).thenReturn(notifications);

        List<Notification> result = notificationService.getAllNotifications();

        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    public void testGetNotificationsByType() {
        String type = "Type1";
        LocalDateTime now = LocalDateTime.now();
        Notification notification1 = new Notification("Title1", "Message1", type, now);
        Notification notification2 = new Notification("Title2", "Message2", type, now);
        List<Notification> notifications = List.of(notification1, notification2);

        when(notificationRepository.findByType(type)).thenReturn(notifications);

        List<Notification> result = notificationService.getNotificationsByType(type);

        assertEquals(2, result.size());
        verify(notificationRepository, times(1)).findByType(type);
    }

    @Test
    public void testAddNotification() {
        String title = "Title1";
        String message = "Message1";
        String type = "Type1";
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification(title, message, type, now);

        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification result = notificationService.addNotification(title, message, type);

        assertEquals(title, notification.getTitle());
        assertEquals(message, notification.getMessage());
        assertEquals(type, notification.getType());
        assertEquals(now.withNano(0), result.getTimestamp().withNano(0)); // Ensure a timestamp is set

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
}