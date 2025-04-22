package com.example.demo.repositories;

import com.example.demo.entity.DTO.VolHistoryProjection;
import com.example.demo.entity.VolunteerHistoryJ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistoryJ, Long> {

    @Query(value = """
        SELECT 
            vh.HistoryID AS id,
            u.full_name AS name,
            u.email AS email,
            u.phone_number AS phoneNumber,
            e.event_name AS eventName,
            e.event_date AS eventDate,
            e.description AS description,
            e.location AS location,
            e.required_skills AS skills,
            e.urgency AS urgency,
            vh.HoursVolunteered AS hoursVolunteered,
            vh.status AS status
        FROM volunteer_history vh
        JOIN user_info u ON vh.UserID = u.userid
        JOIN event_details e ON vh.EventID = e.eventid
        """, nativeQuery = true)
    List<VolHistoryProjection> fetchVolunteerHistoryTable();

    @Query(value = """
        SELECT 
            vh.HistoryID AS id,
            u.full_name AS name,
            u.email AS email,
            u.phone_number AS phoneNumber,
            e.event_name AS eventName,
            e.event_date AS eventDate,
            e.description AS description,
            e.location AS location,
            e.required_skills AS skills,
            e.urgency AS urgency,
            vh.HoursVolunteered AS hoursVolunteered,
            vh.status AS status
        FROM volunteer_history vh
        JOIN user_info u ON vh.UserID = u.userid
        JOIN event_details e ON vh.EventID = e.eventid
        """, nativeQuery = true)
    Optional<VolHistoryProjection> fetchVolunteerHistoryById(@Param("id") Long id);
}
