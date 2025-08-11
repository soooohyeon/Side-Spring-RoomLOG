package com.example.roomlog.dto.user;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.user.SocialType;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
// 마이페이지 - 유저 정보
public class UserInfoDTO {
	private long userId;
	private int socialTypeId;
	private String userEmail;
	private String userNickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private int isAgeVisible;
	private LocalDate userBirth;
	private String formatBirth;
	private String userAge;
	private String userIntro;
	private String profileImgPath;
	private String profileImgUuid;
	private long communityCount;
	private int followerCount;
	private int followCount;
	
	@QueryProjection
	public UserInfoDTO(long userId, int socialTypeId, String userEmail, String userNickname, int isAgeVisible,
			LocalDate userBirth, String formatBirth, String userAge, String userIntro, String profileImgPath,
			String profileImgUuid, long communityCount, int followerCount, int followCount) {
		this.userId = userId;
		this.socialTypeId = socialTypeId;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userBirth = userBirth;
		this.formatBirth = formatBirth;
		this.userAge = userAge;
		this.userIntro = userIntro;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.communityCount = communityCount;
		this.followerCount = followerCount;
		this.followCount = followCount;
	}
	
}
