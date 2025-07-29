package com.example.roomlog.domain.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.roomlog.type.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tbl_user")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "social_type_id", nullable = false)
	private SocialType socialType;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	@Column(unique = true, nullable = false)
	private String userEmail;
	@Column(unique = true, nullable = false)
	private String userNickname;
	@Column(nullable = false)
	private LocalDate userBirth;
	@Column(nullable = false)
	private int isAgeVisible = 1;
	private String userIntro;
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime userJoinDate;
	
	@Builder
	public User(SocialType socialType, String userEmail, String userNickname, LocalDate userBirth, int isAgeVisible) {
		this.socialType = socialType;
		this.userRole = UserRole.USER;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.isAgeVisible = isAgeVisible;
	}

	// 회원가입 시 한줄 소개 저장
	public void saveUserIntro(String userIntro) {
		this.userIntro = userIntro;
	}

	// 닉네임, 나이 노출 여부, 한줄 소개 업데이트
	public void updateUserInfo(String userNickname, int isAgeVisible, String userIntro) {
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userIntro = userIntro;
	}
	
}
