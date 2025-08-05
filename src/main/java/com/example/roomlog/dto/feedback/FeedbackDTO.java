package com.example.roomlog.dto.feedback;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class FeedbackDTO {

	private long feedbackId;
	private long userId;
	private int feedbackType;
	private String feedbackTitle;
	private String feedbackContent;
	private LocalDateTime userRegistDate;
	
}
