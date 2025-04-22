package com.example.demo.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventReportEntry {
    private String eventName;
    private LocalDate eventDate;
    private String volunteerName;
    private String role;
}