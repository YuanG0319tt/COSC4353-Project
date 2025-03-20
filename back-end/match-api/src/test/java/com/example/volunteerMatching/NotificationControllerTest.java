package com.example.volunteerMatching.controllers;

import com.example.volunteerMatching.controllers.NotificationController;
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
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.format.DateTimeFormatter;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        Notification notification1 = new Notification("Reminder", "Meeting at 3 PM", "notification", now);
        Notification notification2 = new Notification("Alert", "System maintenance tonight", "announcement", now);
        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifications()).thenReturn(notifications);

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'title':'Reminder','message':'Meeting at 3 PM','type':'notification','timestamp':'" + notification1.getTimestamp() + "'},{'title':'Alert','message':'System maintenance tonight','type':'announcement','timestamp':'" + notification2.getTimestamp() + "'}]"));
    }

    @Test
    public void testGetNotificationsByType() throws Exception {
        Notification notification = new Notification("Reminder", "Meeting at 3 PM", "notification", LocalDateTime.now().withNano(0));
        List<Notification> notifications = Arrays.asList(notification);

        when(notificationService.getNotificationsByType("notification")).thenReturn(notifications);

        mockMvc.perform(get("/notifications/notification"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'title':'Reminder','message':'Meeting at 3 PM','type':'notification','timestamp':'" + notification.getTimestamp() + "'}]"));
    }

    @Test
    public void testAddNotification() throws Exception {
        Notification notification = new Notification("Maintenance", "Scheduled server downtime", "announcement", LocalDateTime.now());

        when(notificationService.addNotification(any(String.class), any(String.class), any(String.class)))
            .thenAnswer(invocation -> new Notification(
                    invocation.getArgument(0), // title
                    invocation.getArgument(1), // message
                    invocation.getArgument(2), // type
                    LocalDateTime.now()));

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));

        mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Maintenance\",\"message\":\"Scheduled server downtime\",\"type\":\"announcement\",\"timestamp\":\"" + now + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Maintenance"))
                .andExpect(jsonPath("$.message").value("Scheduled server downtime"))
                .andExpect(jsonPath("$.type").value("announcement"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
