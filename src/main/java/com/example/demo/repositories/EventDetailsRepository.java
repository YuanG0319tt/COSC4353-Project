package com.example.demo.repositories;

import com.example.demo.entity.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Integer> {
    Optional<EventDetails> findByEventNameIgnoreCase(String eventName);
}
