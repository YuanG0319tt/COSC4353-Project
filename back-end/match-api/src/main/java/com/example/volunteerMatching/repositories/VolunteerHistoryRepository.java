package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.VolunteerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistory, Long> {
    
    // This query joins the tables to get all the data you need
    @Query("SELECT vh FROM VolunteerHistory vh " +
           "JOIN FETCH vh.userCredentials uc " +
           "JOIN FETCH uc.userInfo ui " +
           "JOIN FETCH vh.eventDetails ed")
    List<VolunteerHistory> findAllWithUserAndEventDetails();
    
    // Or find by user ID
    List<VolunteerHistory> findByUserCredentials_UserId(Long userId);
}