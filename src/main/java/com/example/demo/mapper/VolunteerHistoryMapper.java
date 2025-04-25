package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.VolunteerHistory;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VolunteerHistoryMapper extends BaseMapper<VolunteerHistory> {

    @Select("SELECT * FROM volunteer_history WHERE name = #{volunteerName} AND event_name = #{eventName}")
    List<VolunteerHistory> findByNameAndEvent(@Param("volunteerName") String volunteerName,
                                              @Param("eventName") String eventName);

    @Insert("""
        INSERT INTO volunteer_history (
            UserID, EventID, event_name, name,
            participation_date, hours_volunteered, status
        )
        VALUES (
            #{userId}, #{eventId}, #{eventName}, #{name},
            #{participationDate}, #{hoursVolunteered}, #{status}
        )
    """)
    int insertHistory(VolunteerHistory history);

}
