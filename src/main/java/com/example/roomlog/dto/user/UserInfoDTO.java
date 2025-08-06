package com.example.roomlog.dto.user;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.user.SocialType;

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
	private SocialType socialType;
	private String userEmail;
	private String userNickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private int isAgeVisible;
	private LocalDate userBirth;
	private String userAge;
	private String userIntro;
	private String profileImgPath;
	private String profileImgUuid;
	private long communityCount;
	private int followerCount;
	private int followCount;

//	@QueryProjection
	
}
