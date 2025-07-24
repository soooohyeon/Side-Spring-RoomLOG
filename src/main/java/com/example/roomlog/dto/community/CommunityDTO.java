package com.example.roomlog.dto.community;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.CommunityImg;
import com.example.roomlog.domain.user.User;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommunityDTO {
	
	private long communityId;
	private long userId;
	private User user;
	private String communityTitle;
	private String communityContent;
	private CommunityImg communityImg;
	private List<String> tags;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime modifiedDate;

	public Community toEntityCommunity() {
		return Community.builder()
				.user(user)
				.communityTitle(communityTitle)
				.communityContent(communityContent)
				.build();
	}
	
	@QueryProjection
	public CommunityDTO(long communityId, User user, String communityTitle, String communityContent, CommunityImg communityImg,
			LocalDateTime createDate, LocalDateTime modifiedDate) {
		super();
		this.communityId = communityId;
		this.user = user;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityImg = communityImg;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}
	
}
