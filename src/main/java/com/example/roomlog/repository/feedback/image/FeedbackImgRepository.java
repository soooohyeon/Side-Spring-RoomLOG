package com.example.roomlog.repository.feedback.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.feedback.image.FeedbackImg;

@Repository
public interface FeedbackImgRepository extends JpaRepository<FeedbackImg, Long>{
	
	
	
}
