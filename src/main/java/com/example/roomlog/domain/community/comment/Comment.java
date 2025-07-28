package com.example.roomlog.domain.community.comment;

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
	@JoinColumn(name = "user_id", nullable = false)
	private User writerUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", nullable = false)
	private Community community;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_user_id")
	private User parentUser;

	@Column(length = 700, nullable = false)
	private String commentContent;
	@Column(nullable = false)
	private int isDeleted = 0;
	
	@Builder
	public Comment(User writerUser, Community community, User parentUser, String commentContent) {
		this.writerUser = writerUser;
		this.community = community;
		this.parentUser = parentUser;
		this.commentContent = commentContent;
	}

	// 댓글 수정
	public void updateComment (String commentContent) {
		this.commentContent = commentContent;
	}
	
	// 댓글 삭제
	public void deleteParentComment () {
		this.isDeleted = 1;
	}
	
	// 대댓글이 존재하는 댓글의 유저 탈퇴시
	public void quitUser () {
		this.isDeleted = 2;
	}
	
}
