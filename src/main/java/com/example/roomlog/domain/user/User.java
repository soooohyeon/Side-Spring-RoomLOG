package com.example.roomlog.domain.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_user")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "social_type_id", nullable = false)
	private SocialType socialType;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_img_id")
	private ProfileImg profileImg;

	@NotNull
	@Column(unique = true, nullable = false)
	private String userEmail;
	@NotNull
	@Column(unique = true, nullable = false)
	private String userNickname;
	@NotNull
	@Column(nullable = false)
	private LocalDateTime userBirth;
	@NotNull
	@Column(nullable = false)
	private int isAgeVisible = 1;
	private String userIntro;
	@NotNull
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime userJoinDate;
	
	@Builder
	public User(SocialType socialType, String userEmail, String userNickname, LocalDateTime userBirth, int isAgeVisible) {
		this.socialType = socialType;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.isAgeVisible = isAgeVisible;
	}

//	닉네임, 나이 노출 여부, 한줄 소개 업데이트
	public void updateUserInfo(String userNickname, int isAgeVisible, String userIntro) {
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userIntro = userIntro;
	}
	
//	프로필 사진 업데이트
	public void updateProfileImg(ProfileImg profileImg) {
	    this.profileImg = profileImg;
	}
}
