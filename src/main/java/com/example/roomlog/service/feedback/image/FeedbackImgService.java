package com.example.roomlog.service.feedback.image;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.feedback.Feedback;
import com.example.roomlog.domain.feedback.image.FeedbackImg;
import com.example.roomlog.repository.feedback.image.FeedbackImgRepository;
import com.example.roomlog.util.ImageUploadUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FeedbackImgService {

	private FeedbackImgRepository feedbackImgRepository;
	private final ImageUploadUtils imageUploadUtils;
	
	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;
	
	public FeedbackImg formatFeedbackImg(Feedback feedback, MultipartFile image) throws IllegalStateException, IOException {
		Map<String, Object> img = imageUploadUtils.formatImage("feedback", image);
		
		FeedbackImg feedbackImg = FeedbackImg.builder()
			.feedback(feedback)
			.feedbackImgOriginal((String) img.get("originalImgName"))
			.feedbackImgUuid((String) img.get("imgUuid"))
			.feedbackImgPath((String) img.get("setfileDir"))
			.build();
		
		return feedbackImg;
	}
	
}
