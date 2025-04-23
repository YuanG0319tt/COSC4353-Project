package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

/**
 * Represents a record of a user's participation in an event.
 */
@Data
@TableName("volunteerhistory")
public class VolunteerHistory {

    /** Primary key */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /** User who participated */
    @TableField("user_id")
    private Integer uid;

    /** Associated event ID */
    @TableField("event_id")
    private Integer eventId;

    /** Name of the event (denormalized for history) */
    @TableField("event_name")
    private String eventName;

    @TableField("volunteer_name")
    private String volunteerName;

    /** When the user participated */
    @JsonFormat(pattern = "MMM dd, yyyy", timezone = "GMT+8", locale = "en_US")
    @TableField("participation_date")
    private Timestamp participationDate;

    /** Role the user played in the event */
    private String role;

    /** Hours contributed */
    private Integer hours;

    /** Event description (denormalized) */
    private String description;

    /** Event location (denormalized) */
    private String location;

    /** Required skills for the event (denormalized) */
    @TableField("required_skills")
    private String requiredSkills;

    /** Urgency level (denormalized) */
    private String urgency;

    /** Participation status (e.g. Attended / Not Attend) */
    @TableField("participation_status")
    private String participationStatus;

    /** Completion status (e.g. Completed / Pending) */
    @TableField("completion_status")
    private String completionStatus;
}
