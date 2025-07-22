package com.example.roomlog.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.type.UserRole;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class UserDTO {
	private long userId;
	private SocialType socialType;
	private ProfileImg profileImg;
	private UserRole userRoleS;
	private String userEmail;
	private String userNickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate userBirth;
	private int isAgeVisible = 1;
	private String userIntro;
	private LocalDateTime userJoinDate;

	public User toEntity() {
		return User.builder()
				.socialType(socialType)
				.userEmail(userEmail)
				.userNickname(userNickname)
				.userBirth(userBirth)
				.isAgeVisible(isAgeVisible)
				.build();
	}

	@QueryProjection
	public UserDTO(long userId, SocialType socialType, ProfileImg profileImg, String userEmail, String userNickname,
			LocalDate userBirth, int isAgeVisible, String userIntro, LocalDateTime userJoinDate) {
		super();
		this.userId = userId;
		this.socialType = socialType;
		this.profileImg = profileImg;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.isAgeVisible = isAgeVisible;
		this.userIntro = userIntro;
		this.userJoinDate = userJoinDate;
	}
	
}
