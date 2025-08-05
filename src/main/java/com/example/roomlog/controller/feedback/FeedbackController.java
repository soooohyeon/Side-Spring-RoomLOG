package com.example.roomlog.controller.feedback;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.feedback.FeedbackDTO;
import com.example.roomlog.service.feedback.FeedbackService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

	private final FeedbackService feedbackService;
	
	// 건의사항 이동
	@GetMapping("/feedback-regist")
	public String feedbackPage() {
		return "feedback/feedback";
	}

	// 건의사항 이동
	@PostMapping("/feedback-registOk")
	public String insertFeedback(FeedbackDTO feedbackDTO, List<MultipartFile> images, HttpSession session) {
		long userId = SessionUtils.getUserId(session);
		feedbackDTO.setUserId(userId);
		System.out.println("피드백 컨트롤러 +++++++++++++++");
		
		try {
			feedbackService.insertFeedback(feedbackDTO, images);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/feedback/feedback-regist?registOk=true";
	}
	
}
