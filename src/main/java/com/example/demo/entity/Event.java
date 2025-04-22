package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@TableName("event")
@Data
public class Event {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String location;

    @JsonFormat(pattern = "MMM dd, yyyy", timezone = "GMT+8", locale = "en_US")
    private Timestamp date;

    private String requiredSkills;

    private String urgency;


}