package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Event;
import com.example.demo.entity.VolunteerHistory;
import com.example.demo.mapper.EventMapper;
import com.example.demo.service.EventService;
import com.example.demo.service.VolunteerHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.sql.Timestamp;

@Service
public class EventServiceImpl
        extends ServiceImpl<EventMapper, Event>
        implements EventService
{

    @Autowired
    private VolunteerHistoryService volunteerHistoryService;

    /**
     * Create an Event _and_ automatically insert a stub row
     * into volunteer_history for it.
     */
    @Override
    @Transactional
    public boolean save(Event event) {
        // 1) insert into event table
        boolean ok = super.save(event);
        if (!ok) {
            return false;
        }

        // 2) now that event.getId() is populated, build the history record
        VolunteerHistory h = new VolunteerHistory();
        h.setEventId(event.getId());
        // if you have a creator UID on Event, use that; otherwise pick whatever default
        h.setUserId(event.getId() != null
                ? event.getId()
                : /* fallback UID */ 0);
        // seed defaults:
        //h.setVolunteerName();
        Timestamp ts = event.getDate();
        LocalDate localDate = ts.toLocalDateTime().toLocalDate();
        h.setParticipationDate(Date.valueOf(localDate));
        h.setEventName(event.getName());
        h.setHoursVolunteered(0);
        h.setStatus("Pending");

        // 3) insert into volunteer_history
        boolean histOk = volunteerHistoryService.save(h);
        if (!histOk) {
            // throwing here will roll back both inserts
            throw new RuntimeException("Failed to create VolunteerHistory for Event " + event.getId());
        }

        return true;
    }
}
