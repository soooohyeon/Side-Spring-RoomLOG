package com.example.roomlog.dto.community.comment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommentDTO {

	private long commentId;
	private long userId;
	private long communityId;
	private Long parentCommentId;
	private String commentContent;
	private int isDeleted = 0;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
	
	@QueryProjection
	public CommentDTO(long commentId, long userId, long communityId, Long parentCommentId,
			String commentContent, int isDeleted, LocalDateTime createDate, LocalDateTime modifiedDate) {
		super();
		this.commentId = commentId;
		this.communityId = communityId;
		this.communityId = communityId;
		this.parentCommentId = parentCommentId;
		this.commentContent = commentContent;
		this.isDeleted = isDeleted;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}
	
}
