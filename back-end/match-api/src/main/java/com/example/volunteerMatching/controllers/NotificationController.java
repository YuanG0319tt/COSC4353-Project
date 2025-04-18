package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.Notification;
import com.example.volunteerMatching.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get all notifications
    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Get notifications by type (announcements or notifications)
    @GetMapping("/{type}")
    public List<Notification> getNotificationsByType(@PathVariable String type) {
        return notificationService.getNotificationsByType(type);
    }

    // Add a new notification
    @PostMapping
    public ResponseEntity<Notification> addNotification(@RequestBody Notification notification) {
        Notification savedNotification = notificationService.addNotification(notification.getTitle(), notification.getMessage(), notification.getType());
        return ResponseEntity.ok(savedNotification);
    }
}
