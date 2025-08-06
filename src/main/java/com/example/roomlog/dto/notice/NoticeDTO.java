package com.example.roomlog.dto.notice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class NoticeDTO {

	private long noticeId;
	private long userId;
	private String noticeTitle;
	private String noticeContent;
	private LocalDateTime createDate;
	private List<String> images;

	@QueryProjection
	public NoticeDTO(long noticeId, long userId, String noticeTitle, String noticeContent,
			LocalDateTime createDate) {
		this.noticeId = noticeId;
		this.userId = userId;
		this.noticeTitle = noticeTitle;
		this.noticeContent = noticeContent;
		this.createDate = createDate;
	}
	
}
