package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.VolunteerHistory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistory, Long> {

    // Efficient fetching using EntityGraph to avoid N+1 problems
    @EntityGraph(attributePaths = {"userCredentials", "userCredentials.userInfo", "eventDetails"})
    List<VolunteerHistory> findAll();

    // Find by user ID (fetch related entities)
    @EntityGraph(attributePaths = {"userCredentials", "userCredentials.userInfo", "eventDetails"})
    List<VolunteerHistory> findByUserCredentials_UserId(Long userId);
}
