package com.example.roomlog.domain.community;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class CommunityHashtagId implements Serializable {

	private Long communityId;
	private Long hashtagId;
	
	public CommunityHashtagId(Long communityId, Long hashtagId) {
		this.communityId = communityId;
		this.hashtagId = hashtagId;
	}
	
}
