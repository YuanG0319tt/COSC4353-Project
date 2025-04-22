package com.example.demo.controller;

import com.example.demo.entity.Notice;
import com.example.demo.service.NoticetService;
import com.example.demo.utils.HttpMessage;
import com.example.demo.utils.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(NoticeController.class)
public class NoticeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoticetService noticeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListNotices() throws Exception {
        // Create a sample notice to return from the mocked service.
        Notice notice1 = new Notice();
        notice1.setId(1);
        notice1.setTitle("Test Title");
        notice1.setMessage("Test Message");
        notice1.setType("announcement");
        notice1.setCreateTime(new Timestamp(System.currentTimeMillis()));

        List<Notice> notices = new ArrayList<>();
        notices.add(notice1);

        // Mock the service layer to return the sample notices.
        Mockito.when(noticeService.list()).thenReturn(notices);

        // Perform GET /api/notifications/list and validate the response.
        mockMvc.perform(MockMvcRequestBuilders.get("/api/notifications/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value(HttpMessage.SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Test Title"));
    }

    @Test
    public void testAddNoticeSuccess() throws Exception {
        // Create a sample notice that will be sent in the request.
        Notice newNotice = new Notice();
        newNotice.setTitle("New Notice");
        newNotice.setMessage("New Message");
        newNotice.setType("announcement");

        // When the service.save() method is called, return true.
        Mockito.when(noticeService.save(Mockito.any(Notice.class))).thenReturn(true);

        // Perform POST /api/notifications/add with the JSON payload.
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notifications/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNotice)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.SUCCESS))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value(HttpMessage.SUCCESS));
    }

    @Test
    public void testAddNoticeFailure() throws Exception {
        // Create a sample notice that will be sent in the request.
        Notice newNotice = new Notice();
        newNotice.setTitle("New Notice");
        newNotice.setMessage("New Message");
        newNotice.setType("announcement");

        // When the service.save() method is called, return false.
        Mockito.when(noticeService.save(Mockito.any(Notice.class))).thenReturn(false);

        // Perform POST /api/notifications/add with the JSON payload.
        mockMvc.perform(MockMvcRequestBuilders.post("/api/notifications/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newNotice)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(HttpStatus.FAILED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value(HttpMessage.INSERT_FAILED));
    }
}
