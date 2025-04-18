package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.VolunteerHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerHistoryRepository extends JpaRepository<VolunteerHistory, Long> {
}
