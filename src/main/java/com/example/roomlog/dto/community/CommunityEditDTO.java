package com.example.roomlog.dto.community;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.roomlog.dto.community.image.CommunityImgDTO;
import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class CommunityEditDTO {
	
	private long communityId;
	private String communityTitle;
	private String communityContent;
	private List<String> tags;
	private List<String> deleteImgId;
	private List<CommunityImgDTO> communityImages;
	
	@QueryProjection
	public CommunityEditDTO(long communityId, String communityTitle, String communityContent,
			List<CommunityImgDTO> communityImages, List<String> tags) {
		super();
		this.communityId = communityId;
		this.communityTitle = communityTitle;
		this.communityContent = communityContent;
		this.communityImages = communityImages;
		this.tags = tags;
	}
	
}
