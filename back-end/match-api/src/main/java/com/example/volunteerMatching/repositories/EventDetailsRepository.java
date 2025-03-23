package com.example.volunteerMatching.repositories;

import com.example.volunteerMatching.models.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Integer> {
}