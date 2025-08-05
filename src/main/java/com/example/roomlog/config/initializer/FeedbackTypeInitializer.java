package com.example.roomlog.config.initializer;

import java.util.Arrays;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.feedback.FeedbackType;
import com.example.roomlog.repository.feedback.FeedbackTypeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeedbackTypeInitializer implements ApplicationRunner {

	private final FeedbackTypeRepository feedbackTypeRepository;

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		if (feedbackTypeRepository.count() == 0) {
			Arrays.asList(
				new FeedbackType(1, "건의"),
				new FeedbackType(2, "오류 신고"),
				new FeedbackType(3, "유저 신고"),
				new FeedbackType(4, "기타")
			).forEach(feedbackTypeRepository::save);
		}
	}
	
}
