package com.example.volunteerMatching.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "VolunteerHistory")
@NoArgsConstructor
@Getter
@Setter
public class VolunteerHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private Long historyId;
    
    @Column(name = "ParticipationDate")
    private LocalDate participationDate;
    
    // Define relationship with UserCredentials/UserProfile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    private UserCredentials userCredentials;
    
    // Define relationship with EventDetails
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EventID")  
    private EventDetails eventDetails;
    
    // Constructor, getters, and setters
    public VolunteerHistory(UserCredentials userCredentials, EventDetails eventDetails, LocalDate participationDate) {
        this.userCredentials = userCredentials;
        this.eventDetails = eventDetails;
        this.participationDate = participationDate;
    }
}