package com.example.volunteerMatching;

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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    //private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();
    }

    @Test
    public void testGetAllNotifications() throws Exception {
        LocalDateTime timestamp = LocalDateTime.of(2025, 3, 22, 15, 00, 00);
        Notification n1 = new Notification("Reminder", "Meeting at 3 PM", "notification", timestamp);
        Notification n2 = new Notification("Alert", "System maintenance tonight", "announcement", timestamp);

        when(notificationService.getAllNotifications()).thenReturn(List.of(n1, n2));

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Reminder"))
                .andExpect(jsonPath("$[0].message").value("Meeting at 3 PM"))
                .andExpect(jsonPath("$[0].type").value("notification"))
                .andExpect(jsonPath("$[1].title").value("Alert"))
                .andExpect(jsonPath("$[1].message").value("System maintenance tonight"))
                .andExpect(jsonPath("$[1].type").value("announcement"))
                .andExpect(jsonPath("$[0].timestamp").value("2025-03-22T15:00:00"));
    }

    @Test
    public void testGetNotificationsByType() throws Exception {
        LocalDateTime timestamp = LocalDateTime.of(2025, 3, 22, 15, 00, 00);
        Notification notification = new Notification("Reminder", "Meeting at 3 PM", "notification", timestamp);

        when(notificationService.getNotificationsByType("notification")).thenReturn(List.of(notification));

        mockMvc.perform(get("/notifications/notification"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Reminder"))
                .andExpect(jsonPath("$[0].type").value("notification"));
    }

    // @Test
    // public void testAddNotification() throws Exception {
    //     LocalDateTime fixedNow = LocalDateTime.of(2025, 3, 22, 15, 30, 00);
    //     Notification expectedNotification = new Notification("Maintenance", "Scheduled server downtime", "announcement", fixedNow);

    //     when(notificationService.addNotification(any(), any(), any()))
    //             .thenReturn(expectedNotification);

    //     String jsonRequest = """
    //         {
    //           "title": "Maintenance",
    //           "message": "Scheduled server downtime",
    //           "type": "announcement"
    //         }
    //     """;

    //     mockMvc.perform(post("/notifications")
    //             .contentType(MediaType.APPLICATION_JSON)
    //             .content(jsonRequest))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$.title").value("Maintenance"))
    //             .andExpect(jsonPath("$.message").value("Scheduled server downtime"))
    //             .andExpect(jsonPath("$.type").value("announcement"))
    //             .andExpect(jsonPath("$.timestamp").value("2025-03-22T15:30:00"));
    // }
}
