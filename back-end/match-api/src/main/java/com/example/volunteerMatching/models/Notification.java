package com.example.volunteerMatching.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NotificationID")
    private Integer id;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "Message", nullable = false)
    private String message;

    @Column(name = "NotificationType", nullable = false)
    private String type; // "announcement" or "notification"

    @Column(name = "CreatedAt", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    public Notification() {
        this.timestamp = LocalDateTime.now(); // Default to current time
    }

    public Notification(String title, String message, String type, LocalDateTime timestamp) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
    public void setType(String type) { this.type = type; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
