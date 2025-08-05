package com.example.roomlog.repository.feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.feedback.FeedbackType;


@Repository
public interface FeedbackTypeRepository extends JpaRepository<FeedbackType, Integer> {
	
	// 건의 유형 검색
	FeedbackType findByFeedbackTypeId(int feedbackTypeId);
	
}
