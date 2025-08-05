package com.example.roomlog.domain.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_feedback_type")
@Getter @ToString
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackType {

	@Id
	private int feedbackTypeId;
	@Column(unique = true, nullable = false)
	private String feedbackTypeName;
	
	@Builder
	public FeedbackType(int feedbackTypeId, String feedbackTypeName) {
		this.feedbackTypeId = feedbackTypeId;
		this.feedbackTypeName = feedbackTypeName;
	}
	
}
