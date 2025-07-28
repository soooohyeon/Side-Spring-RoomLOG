package com.example.roomlog.dto.community;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommunityViewDTO {
	
	private long communityId;
	private String communityTitle;
	private String communityContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime modifiedDate;
	private long userId;
	private String userNickname;
	private int isAgeVisible;
	private LocalDate userBirth;
	private String userAge;
	private String userIntro;
	private String profileImgPath;
	private String profileImgUuid;
	private List<String> images;
	private boolean isScrapped;
	private long scrapCount;
	private boolean isFollowed;
	private List<String> tags;
	
	@QueryProjection
	public CommunityViewDTO(long communityId, String communityTitle, String communityContent, LocalDateTime createDate,
			LocalDateTime modifiedDate, long userId, String userNickname, int isAgeVisible, LocalDate userBirth,
			String userAge, String userIntro, String profileImgPath, String profileImgUuid, List<String> images,
			boolean isScrapped, long scrapCount, boolean isFollowed, List<String> tags) {
		super();
		this.communityId = communityId;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
		this.userId = userId;
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userBirth = userBirth;
		this.userAge = userAge;
		this.userIntro = userIntro;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.images = images;
		this.isScrapped = isScrapped;
		this.scrapCount = scrapCount;
		this.isFollowed = isFollowed;
		this.tags = tags;
	}
	
}
