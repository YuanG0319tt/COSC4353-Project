package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "EventDetails")
@NoArgsConstructor
@Getter
@Setter
public class EventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID")
    private Long eventId;
    
    @Column(name = "EventName")
    private String eventName;
    
    @Column(name = "EventDate")
    private LocalDate eventDate;
    
    @Column(name = "Location")
    private String location;

    @Column(name = "Description")
    private String description;

    @Column(name = "RequiredSkills")
    private String requirements;

    @Column(name = "Urgency")
    private String urgency;

    // Relationship with VolunteerHistory
    @OneToMany(mappedBy = "eventDetails")
    private List<VolunteerHistory> volunteerHistories;
}