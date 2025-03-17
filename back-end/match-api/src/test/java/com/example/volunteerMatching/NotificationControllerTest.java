package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.models.Notification;
import com.example.volunteerMatching.services.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NotificationControllerTest {
    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    void testAddNotification() throws Exception {
        Notification notification = new Notification("System Update", "New features added", "announcement");
        when(notificationService.addNotification(anyString(), anyString(), anyString())).thenReturn(notification);

        mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"System Update\", \"message\":\"New features added\", \"type\":\"announcement\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("System Update"));
    }

    @Test
    void testGetAllNotifications() throws Exception {
        Notification notification1 = new Notification("Reminder", "Meeting at 3 PM", "notification");
        Notification notification2 = new Notification("Alert", "System maintenance tonight", "announcement");

        when(notificationService.getAllNotifications()).thenReturn(List.of(notification1, notification2));

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Reminder"))
                .andExpect(jsonPath("$[1].title").value("Alert"));
    }

    @Test
    void testGetNotificationsByType() throws Exception {
        Notification notification = new Notification("Maintenance", "Scheduled server downtime", "announcement");
        when(notificationService.getNotificationsByType("announcement")).thenReturn(List.of(notification));

        mockMvc.perform(get("/notifications/announcement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Maintenance"));
    }
}
