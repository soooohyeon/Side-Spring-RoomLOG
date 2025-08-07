package com.example.roomlog.dto.follow;

import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class FollowDTO {
	
	private long followId;
	private long userId;
	private String userNickname;
	private String userIntro;
	private String profileImgPath;
	private String profileImgUuid;
	private boolean isFollowed;
	
	@QueryProjection
	public FollowDTO(long followId, long userId, String userNickname, String userIntro, String profileImgPath,
			String profileImgUuid, boolean isFollowed) {
		this.followId = followId;
		this.userId = userId;
		this.userNickname = userNickname;
		this.userIntro = userIntro;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.isFollowed = isFollowed;
	}
	
}
