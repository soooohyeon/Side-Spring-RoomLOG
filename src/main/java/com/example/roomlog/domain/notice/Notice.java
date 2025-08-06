package com.example.roomlog.domain.notice;

import com.example.roomlog.domain.common.BaseTimeEntity;
import com.example.roomlog.domain.user.User;

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
@Table(name = "tbl_notice")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long noticeId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String noticeTitle;
	@Column(length = 3500, nullable = false)
	private String noticeContent;
	
	@Builder
	public Notice(User user, String noticeTitle, String noticeContent) {
		this.user = user;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
	}
	
}
