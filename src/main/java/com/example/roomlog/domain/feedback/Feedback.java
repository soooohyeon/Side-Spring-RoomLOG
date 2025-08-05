package com.example.roomlog.domain.feedback;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.roomlog.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "tbl_feedback")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long feedbackId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feedbackTypeId", nullable = false)
	private FeedbackType feedbackType;

	
	@Column(nullable = false)
	private String feedbackTitle;
	@Column(length = 2000, nullable = false)
	private String feedbackContent;
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime feedbackRegistDate;
	
	@Builder
	public Feedback(User user, FeedbackType feedbackType, String feedbackTitle, String feedbackContent) {
		this.user = user;
		this.feedbackType = feedbackType;
		this.feedbackTitle = feedbackTitle;
		this.feedbackContent = feedbackContent;
	}
	
}
