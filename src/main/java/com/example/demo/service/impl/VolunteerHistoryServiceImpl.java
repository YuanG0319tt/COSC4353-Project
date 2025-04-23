package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Event;
import com.example.demo.entity.VolunteerHistory;
import com.example.demo.mapper.EventMapper;
import com.example.demo.mapper.VolunteerHistoryMapper;
import com.example.demo.service.VolunteerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class VolunteerHistoryServiceImpl extends ServiceImpl<VolunteerHistoryMapper, VolunteerHistory> implements VolunteerHistoryService {

    @Autowired
    private VolunteerHistoryMapper volunteerHistoryMapper;
    @Autowired
    private EventMapper eventMapper;

    @Override
    public List<VolunteerHistory> selectList(String eventOrRole, String status, Integer uid) {
        LambdaQueryWrapper<VolunteerHistory> lqw = Wrappers.<VolunteerHistory>lambdaQuery();
        if (uid != 0) {
            lqw.eq(VolunteerHistory::getId,uid);
        }
        if (!(status == null || "".equals(status))) {
            lqw.eq(VolunteerHistory::getCompletionStatus,status);
        }
        if (!(eventOrRole == null || "".equals(eventOrRole))) {
            lqw.like(VolunteerHistory::getEventName,eventOrRole)
                    .or()
                    .like(VolunteerHistory::getRole,eventOrRole);
        }

        return volunteerHistoryMapper.selectList(lqw);
    }

    @Override
    public boolean add(VolunteerHistory volunteerHistory) {
        Event event = eventMapper.selectById(volunteerHistory.getEventId());
        volunteerHistory.setEventName(event.getName());
        volunteerHistory.setDescription(event.getDescription());
        volunteerHistory.setLocation(event.getLocation());
        volunteerHistory.setRequiredSkills(event.getRequiredSkills());
        volunteerHistory.setUrgency(event.getUrgency());
        return volunteerHistoryMapper.insert(volunteerHistory) > 0;
    }
}
