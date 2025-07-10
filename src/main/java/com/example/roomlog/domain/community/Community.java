package com.example.roomlog.domain.community;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_community")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Community {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long communityId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@NotNull
	@Column(nullable = false)
	private String communityTitle;
	@NotNull
	@Column(length = 2000, nullable = false)
	private String communityContent;
	@NotNull
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime communityRegistDate;
	@UpdateTimestamp
	private LocalDateTime communityUpdateDate;
	
	@Builder
	public Community(User user, String communityTitle, String communityContent, LocalDateTime communityRegistDate) {
		this.user = user;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityRegistDate = communityRegistDate;
	}
	
//	게시글 수정
	public void updateCommunity(String communityTitle, String communityContent, LocalDateTime communityUpdateDate) {
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityUpdateDate = communityUpdateDate;
	}
	
}
