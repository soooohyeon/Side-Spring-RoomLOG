package com.example.roomlog.domain.scrap;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
public class ScrapId implements Serializable {

	private Long userId;
	private Long communityId;
	
	public ScrapId(Long userId, Long communityId) {
		this.userId = userId;
		this.communityId = communityId;
	}
	
}
