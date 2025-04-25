package com.example.demo.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "event_details")
public class EventDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    private Integer eventID;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Column(name = "required_skills")
    private String requiredSkills;

    @Column(name = "urgency")
    private int urgency;

    @Column(name = "event_date")
    private Date eventDate;

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

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }
}
