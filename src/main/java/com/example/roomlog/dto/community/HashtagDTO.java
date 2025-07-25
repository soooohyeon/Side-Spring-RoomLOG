package com.example.roomlog.dto.community;

import org.springframework.stereotype.Component;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter @Setter @ToString
@NoArgsConstructor
public class HashtagDTO {
	
	private long communityId;
	private String hashtagName;
	
	@QueryProjection
	public HashtagDTO(long communityId, String hashtagName) {
		super();
		this.communityId = communityId;
		this.hashtagName = hashtagName;
	}
	
}
