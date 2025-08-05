package com.example.roomlog.service.feedback;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.feedback.Feedback;
import com.example.roomlog.domain.feedback.FeedbackType;
import com.example.roomlog.domain.feedback.image.FeedbackImg;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.feedback.FeedbackDTO;
import com.example.roomlog.repository.feedback.FeedbackRepository;
import com.example.roomlog.repository.feedback.FeedbackTypeRepository;
import com.example.roomlog.repository.feedback.image.FeedbackImgRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.service.feedback.image.FeedbackImgService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackService {

	private final FeedbackImgService feedbackImgService;
	
	private final UserRepository userRepository;
	private final FeedbackRepository feedbackRepository;
	private final FeedbackTypeRepository feedbackTypeRepository;
	private final FeedbackImgRepository feedbackImgRepository;
	
	// 건의사항 등록
	public void insertFeedback(FeedbackDTO feedbackDTO, List<MultipartFile> images) throws IllegalStateException, IOException {
		User user = userRepository.findByUserId(feedbackDTO.getUserId()).get();
		FeedbackType feedbackType = feedbackTypeRepository.findByFeedbackTypeId(feedbackDTO.getFeedbackType());
		
		Feedback feedback = Feedback.builder()
			.feedbackType(feedbackType)
			.feedbackTitle(feedbackDTO.getFeedbackTitle())
			.feedbackContent(feedbackDTO.getFeedbackContent())
			.user(user)
			.build();
		feedbackRepository.save(feedback);
		
		// 이미지 등록 처리
		for (MultipartFile image : images) {
			if (image.isEmpty()) { break; }
			FeedbackImg feedbackImg = feedbackImgService.formatFeedbackImg(feedback, image);
			feedbackImgRepository.save(feedbackImg);
		}
	}
	
}
