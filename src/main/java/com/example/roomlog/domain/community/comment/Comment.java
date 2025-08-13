package com.example.roomlog.domain.community.comment;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.common.BaseTimeEntity;
import com.example.roomlog.domain.community.Community;
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
@Table(name = "tbl_comment")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long commentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Community community;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_comment_id")
	private Comment parentComment;

	@Column(length = 700, nullable = false)
	private String commentContent;
	@Column(nullable = false)
	private int isDeleted = 0;
	
	@Builder
	public Comment(User user, Community community, Comment parentComment, String commentContent) {
		this.user = user;
		this.community = community;
		this.parentComment = parentComment;
		this.commentContent = commentContent;
	}

	// 댓글 수정
	public void updateComment (String commentContent) {
		this.commentContent = commentContent;
	}
	
}
