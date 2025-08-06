package com.example.roomlog.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.type.UserRole;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
// 메인 - 유저 랭킹
public class UserDTO {
	private long userId;
	private SocialType socialType;
	private String userEmail;
	private String userNickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate userBirth;
	private int isAgeVisible;
	private String userIntro;
	private LocalDateTime userJoinDate;
	private String profileImgPath;
	private String profileImgUuid;
	private long communityCount;
	private boolean isFollowed;
	private long followerCount;

	@QueryProjection
	public UserDTO(long userId, SocialType socialType, String userEmail, String userNickname,
			LocalDate userBirth, int isAgeVisible, String userIntro, LocalDateTime userJoinDate, String profileImgPath,
			String profileImgUuid, long communityCount, boolean isFollowed, long followerCount) {
		super();
		this.userId = userId;
		this.socialType = socialType;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.isAgeVisible = isAgeVisible;
		this.userIntro = userIntro;
		this.userJoinDate = userJoinDate;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.communityCount = communityCount;
		this.isFollowed = isFollowed;
		this.followerCount = followerCount;
	}
	
}
