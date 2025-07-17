package com.example.roomlog.domain.community;

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
@Table(name = "tbl_community")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long communityId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(nullable = false)
	private String communityTitle;
	@Column(length = 2000, nullable = false)
	private String communityContent;
	
	@Builder
	public Community(User user, String communityTitle, String communityContent) {
		this.user = user;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
	}
	
	// 게시글 수정
	public void updateCommunity(String communityTitle, String communityContent) {
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
	}
	
}
