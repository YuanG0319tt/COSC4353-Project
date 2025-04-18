package com.example.volunteerMatching.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "VolunteerHistory")
public class VolunteerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HistoryID")
    private Integer historyId;

    @Column(name = "UserID")
    private Integer uid;  // 🔁 Keep it as raw ID (FK to user)

    @Column(name = "EventID")
    private Integer eventId;

    @Column(name = "ParticipationDate")
    private LocalDate participationDate;

    @Column(name = "HoursVolunteered")
    private Double hoursVolunteered;

    // Constructors
    public VolunteerHistory() {}

    public VolunteerHistory(Integer uid, Integer eventId, LocalDate participationDate, Double hoursVolunteered) {
        this.uid = uid;
        this.eventId = eventId;
        this.participationDate = participationDate;
        this.hoursVolunteered = hoursVolunteered;
    }

    // Getters and Setters
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public LocalDate getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(LocalDate participationDate) {
        this.participationDate = participationDate;
    }

    public Double getHoursVolunteered() {
        return hoursVolunteered;
    }

    public void setHoursVolunteered(Double hoursVolunteered) {
        this.hoursVolunteered = hoursVolunteered;
    }
}
