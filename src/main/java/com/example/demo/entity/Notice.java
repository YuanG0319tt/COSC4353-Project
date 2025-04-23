package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@TableName("notice")
@Data
public class Notice {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String message;


    private String type;

    @JsonFormat(pattern = "MMM dd, yyyy hh:mm a", timezone = "GMT+8", locale = "en_US")
    private Timestamp createTime;


}