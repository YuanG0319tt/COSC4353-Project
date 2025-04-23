// src/main/java/com/example/demo/dto/VolunteerReportEntry.java
package com.example.demo.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerReportEntry {
    private String volunteerName;
    private String eventName;
    private LocalDate participationDate;
    private String role;
    private Integer hours;
}
