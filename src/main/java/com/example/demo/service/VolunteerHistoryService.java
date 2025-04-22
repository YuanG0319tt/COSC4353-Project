package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.VolunteerHistory;

import java.util.List;

public interface VolunteerHistoryService extends IService<VolunteerHistory> {

    List<VolunteerHistory> selectList(String eventOrRole, String status, Integer uid);

    boolean add(VolunteerHistory volunteerHistory);
}
