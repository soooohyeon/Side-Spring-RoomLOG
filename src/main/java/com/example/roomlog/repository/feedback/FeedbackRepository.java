package com.example.roomlog.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.feedback.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
