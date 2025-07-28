package com.example.roomlog.dto.community;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.example.roomlog.domain.community.image.CommunityImg;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommunityRegistDTO {
	
	private long communityId;
	private long userId;
	private String communityTitle;
	private String communityContent;
	private CommunityImg communityImg;
	private List<String> tags;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime modifiedDate;
	
	@QueryProjection
	public CommunityRegistDTO(long communityId, long userId, String communityTitle, String communityContent, CommunityImg communityImg,
			LocalDateTime createDate, LocalDateTime modifiedDate) {
		super();
		this.communityId = communityId;
		this.userId = userId;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityImg = communityImg;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}
	
}
