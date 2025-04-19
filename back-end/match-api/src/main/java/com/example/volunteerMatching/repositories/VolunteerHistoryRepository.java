package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.VolunteerHistory;
import com.example.volunteerMatching.models.DTO.VolHistoryProjection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistory, Long> {

    @Query(value = """
        SELECT 
            vh.HistoryID AS id,
            u.FullName AS name,
            u.Email AS email,
            u.PhoneNumber AS phoneNumber,
            e.EventName AS eventName,
            e.EventDate AS eventDate,
            e.Description AS description,
            e.Location AS location,
            e.RequiredSkills AS skills,
            e.Urgency AS urgency,
            vh.HoursVolunteered AS hoursVolunteered,
            'Completed' AS status,
            'Attended' AS participationStatus
        FROM VolunteerHistory vh
        JOIN UserInfo u ON vh.UserID = u.UserID
        JOIN EventDetails e ON vh.EventID = e.EventID
        """, nativeQuery = true)
    List<VolHistoryProjection> fetchVolunteerHistoryTable();

    @Query(value = """
        SELECT 
            vh.HistoryID AS id,
            u.FullName AS name,
            u.Email AS email,
            u.PhoneNumber AS phoneNumber,
            e.EventName AS eventName,
            e.EventDate AS eventDate,
            e.Description AS description,
            e.Location AS location,
            e.RequiredSkills AS skills,
            e.Urgency AS urgency,
            vh.HoursVolunteered AS hoursVolunteered,
            'Completed' AS status,
            'Attended' AS participationStatus
        FROM VolunteerHistory vh
        JOIN UserInfo u ON vh.UserID = u.UserID
        JOIN EventDetails e ON vh.EventID = e.EventID
        """, nativeQuery = true)
    Optional<VolHistoryProjection> fetchVolunteerHistoryById(@Param("id") Long id);
}
