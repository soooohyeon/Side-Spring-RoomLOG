package com.example.roomlog.dto.community.comment;

import java.time.LocalDate;
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
public class CommentDTO {

	private long commentId;
	private long communityId;
	private long userId;
	private String userNickname;
	private int isAgeVisible;
	private LocalDate userBirth;
	private String userAge;
	private String profileImgPath;
	private String profileImgUuid;
	private Long parentCommentId;
	private String commentContent;
	private int isDeleted = 0;
	private LocalDateTime createDate;
	private LocalDateTime modifiedDate;
	private List<CommentDTO> childComment;
	
	@QueryProjection
	public CommentDTO(long commentId, long communityId, long userId, String userNickname, int isAgeVisible,
			LocalDate userBirth, String userAge, String profileImgPath, String profileImgUuid, Long parentCommentId,
			String commentContent, int isDeleted, LocalDateTime createDate, LocalDateTime modifiedDate,
			List<CommentDTO> childComment) {
		super();
		this.commentId = commentId;
		this.communityId = communityId;
		this.userId = userId;
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userBirth = userBirth;
		this.userAge = userAge;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.parentCommentId = parentCommentId;
		this.commentContent = commentContent;
		this.isDeleted = isDeleted;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		this.childComment = childComment;
	}
	
}
