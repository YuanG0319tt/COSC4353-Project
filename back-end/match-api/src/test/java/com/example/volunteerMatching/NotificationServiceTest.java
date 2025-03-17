package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Notification;
import com.example.volunteerMatching.repositories.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNotification() {
        Notification notification = new Notification("System Update", "New features added", "announcement", LocalDateTime.now());
        when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

        Notification savedNotification = notificationService.addNotification("System Update", "New features added", "announcement");

        assertNotNull(savedNotification);
        assertEquals("System Update", savedNotification.getTitle());
        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetAllNotifications() {
        Notification notification1 = new Notification("Reminder", "Meeting at 3 PM", "notification", LocalDateTime.now());
        Notification notification2 = new Notification("Alert", "System maintenance tonight", "announcement", LocalDateTime.now());

        when(notificationRepository.findAll()).thenReturn(List.of(notification1, notification2));

        List<Notification> notifications = notificationService.getAllNotifications();

        assertEquals(2, notifications.size());
        assertEquals("Reminder", notifications.get(0).getTitle());
        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testGetNotificationsByType() {
        Notification notification = new Notification("Maintenance", "Scheduled server downtime", "announcement", LocalDateTime.now());
        when(notificationRepository.findByType("announcement")).thenReturn(List.of(notification));

        List<Notification> notifications = notificationService.getNotificationsByType("announcement");

        assertEquals(1, notifications.size());
        assertEquals("Maintenance", notifications.get(0).getTitle());
        verify(notificationRepository, times(1)).findByType("announcement");
    }
}
