package com.example.roomlog.dto.community.image;

import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommunityImgDTO {
	private long communityImgId;
	private String communityImgOriginal;
	private String communityImgUuid;
	private String communityImgPath;
	
	@QueryProjection
	public CommunityImgDTO(long communityImgId, String communityImgOriginal, String communityImgUuid,
			String communityImgPath) {
		super();
		this.communityImgId = communityImgId;
		this.communityImgOriginal = communityImgOriginal;
		this.communityImgUuid = communityImgUuid;
		this.communityImgPath = communityImgPath;
	}
	
}
