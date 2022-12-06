package com.example.travelWebsite.repository;

import com.example.travelWebsite.collections.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}
