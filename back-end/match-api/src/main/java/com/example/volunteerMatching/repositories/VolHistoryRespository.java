package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.VolHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolHistoryRepository extends JpaRepository<VolHistory, Long> {
}