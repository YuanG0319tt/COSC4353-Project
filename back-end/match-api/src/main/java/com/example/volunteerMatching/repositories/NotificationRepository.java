package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByType(String type); // Fetch notifications by type
}
