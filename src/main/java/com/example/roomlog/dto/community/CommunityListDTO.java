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
	private LocalDate userBirth;
	private String profileImgPath;
	private String profileImgUuid;
	private String communityImgPath;
	private String communityImgUuid;
	private long commentCount;
	private boolean isScrapped;
	private long scrapCount;
	private List<String> tags;
	
	@QueryProjection
	public CommunityListDTO(long communityId, long userId, String userNickname, LocalDate userBirth,
			String profileImgPath, String profileImgUuid, String communityTitle, String communityContent,
			String communityImgPath, String communityImgUuid, List<String> tags, long commentCount, long scrapCount,
			boolean isScrapped, LocalDateTime createDate) {
		this.communityId = communityId;
		this.userId = userId;
		this.userNickname = userNickname;
		this.userBirth = userBirth;
		this.profileImgPath = profileImgPath;
		this.profileImgUuid = profileImgUuid;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityImgPath = communityImgPath;
		this.communityImgUuid = communityImgUuid;
		this.tags = tags;
		this.commentCount = commentCount;
		this.scrapCount = scrapCount;
		this.isScrapped = isScrapped;
		this.createDate = createDate;
	}
	
}
