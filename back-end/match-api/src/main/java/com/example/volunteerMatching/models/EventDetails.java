package com.example.volunteerMatching.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EventDetails") // Make sure it matches exactly, including case
public class EventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EventID") // Must match MySQL column
    private Integer eventID;

    @Column(name = "EventName")
    private String eventName;

    @Column(name = "Description")
    private String description;

    @Column(name = "Location")
    private String location;

    @Column(name = "RequiredSkills")
    private String requiredSkills;

    @Column(name = "Urgency")
    private int urgency;

    @Column(name = "EventDate")
    private LocalDate eventDate;

    public EventDetails() {}

    // Getters and Setters
    public Integer getEventID() { return eventID; }
    public void setEventID(Integer eventID) { this.eventID = eventID; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }

    public int getUrgency() { return urgency; }
    public void setUrgency(int urgency) { this.urgency = urgency; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }
}
