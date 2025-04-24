package com.example.demo.controller;

import com.example.demo.entity.DTO.VolHistory;
import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.service.VolHistoryServiceJ;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VolunteerHistoryController.class)
class VolHistoryControllerJTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VolHistoryServiceJ service;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void addVolunteerHistoryReturnsDto() throws Exception {
        VolHistory in = new VolHistory();
        in.setEventDate("2025-04-22");
        in.setHoursVolunteered(2);

        VolHistory out = new VolHistory();
        out.setId(123L);
        out.setStatus("CREATED");

        when(service.addVolHistory(any())).thenReturn(out);

        mockMvc.perform(post("/volunteer-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(in)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(service).addVolHistory(any(VolHistory.class));
    }


    @Test
    void getVolunteerHistoryByIdNotFound() throws Exception {
        when(service.getVolHistoryById(8L))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/volunteer-history/8"))
                .andExpect(status().isNotFound());

        verify(service).getVolHistoryById(8L);
    }

    @Test
    void deleteVolunteerHistoryFound() throws Exception {
        VolHistoryProjection p = mock(VolHistoryProjection.class);
        when(service.getVolHistoryById(9L))
                .thenReturn(Optional.of(p));

        mockMvc.perform(delete("/volunteer-history/9"))
                .andExpect(status().isNoContent());

        verify(service).getVolHistoryById(9L);
        verify(service).deleteVolHistory(9L);
    }

    @Test
    void deleteVolunteerHistoryNotFound() throws Exception {
        when(service.getVolHistoryById(10L))
                .thenReturn(Optional.empty());

        mockMvc.perform(delete("/volunteer-history/10"))
                .andExpect(status().isNotFound());

        verify(service).getVolHistoryById(10L);
        verify(service, never()).deleteVolHistory(anyLong());
    }
}
