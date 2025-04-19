package com.example.volunteerMatching.models.DTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VolHistory {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String eventName;
    private String eventDate;
    private String description;
    private String location;
    private String skills;
    private int urgency;
    private double hoursVolunteered;
    private String participationStatus;
    private String status;
}