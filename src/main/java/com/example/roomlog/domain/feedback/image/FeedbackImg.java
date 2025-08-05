package com.example.roomlog.domain.feedback.image;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.feedback.Feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_feedback_img")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedbackImg {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long feedbackImgId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feedbackId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Feedback feedback;
	@Column(nullable = false)
	private String feedbackImgOriginal;
	@Column(unique = true, nullable = false)
	private String feedbackImgUuid;
	@Column(nullable = false)
	private String feedbackImgPath;
	
	@Builder
	public FeedbackImg(Feedback feedback, String feedbackImgOriginal, String feedbackImgUuid,
			String feedbackImgPath) {
		this.feedback = feedback;
		this.feedbackImgOriginal = feedbackImgOriginal;
		this.feedbackImgUuid = feedbackImgUuid;
		this.feedbackImgPath = feedbackImgPath;
	}

}
