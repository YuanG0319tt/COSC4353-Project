package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

@Data
@TableName("volunteer_history")
public class VolunteerHistory {

    @TableId(value = "HistoryID", type = IdType.AUTO)
    private Long historyId;

    @TableField("UserID")
    private Integer userId;

    @TableField("EventID")
    private Integer eventId;

    @TableField("ParticipationDate")
    private Date participationDate;

    @TableField("HoursVolunteered")
    private Integer hoursVolunteered;

    @TableField("email")
    private String email;

    @TableField("event_date")
    private String eventDate;

    @TableField("event_name")
    private String eventName;

    @TableField("name")
    private String name;

    @TableField("phone_number")
    private String phoneNumber;

    @TableField("status")
    private String status;
}
