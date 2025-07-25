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
public class CommunityListDTO {
	
	private long communityId;
	private String communityTitle;
	private String communityContent;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createDate;
	private long userId;
	private String userNickname;
	private int isAgeVisible;
	private LocalDate userBirth;
	private String userAge;
	private String profileImgPath;
	private String profileImgUuid;
	private String communityImgPath;
	private String communityImgUuid;
	private boolean checkCommunityImg;
	private long commentCount;
	private boolean isScrapped;
	private long scrapCount;
	private List<String> tags;
	
	@QueryProjection
	public CommunityListDTO(long communityId, String communityTitle, String communityContent, LocalDateTime createDate,
			long userId, String userNickname, int isAgeVisible, LocalDate userBirth, String userAge,
			String profileImgPath, String profileImgUuid, String communityImgPath, String communityImgUuid,
			boolean checkCommunityImg, long commentCount, boolean isScrapped, long scrapCount, List<String> tags) {
		super();
		this.communityId = communityId;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.createDate = createDate;
		this.userId = userId;
		this.userNickname = userNickname;
		this.isAgeVisible = isAgeVisible;
		this.userBirth = userBirth;
		this.userAge = userAge;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.communityImgPath = communityImgPath;
		this.communityImgUuid = communityImgUuid;
		this.checkCommunityImg = checkCommunityImg;
		this.commentCount = commentCount;
		this.isScrapped = isScrapped;
		this.scrapCount = scrapCount;
		this.tags = tags;
	}
	
}
