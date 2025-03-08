package com.example.volunteerMatching.services;

import com.example.volunteerMatching.models.Notification;
import com.example.volunteerMatching.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByType(String type) {
        return notificationRepository.findByType(type);
    }

    public Notification addNotification(String title, String message, String type) {
        Notification notification = new Notification(title, message, type, LocalDateTime.now());
        return notificationRepository.save(notification);
    }
}
