package com.example.volunteerMatching.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.volunteerMatching.models.VolHistory;

@Repository
public interface VolHistoryRepository extends JpaRepository<VolHistory, Long> {
}
