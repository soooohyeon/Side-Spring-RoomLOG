package com.example.roomlog.domain.community;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table (name = "tbl_community_hashtag")
@Getter @ToString
public class CommunityHashtag {

	@EmbeddedId
	private CommunityHashtagId id;

    @MapsId(value = "communityId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", nullable = false)
	private Community community;
    @MapsId(value = "hashtagId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id", nullable = false)
	private Hashtag hashtag;
	
	@Builder
	public CommunityHashtag(Community community, Hashtag hashtag) {
		this.community = community;
		this.hashtag = hashtag;
	}
	
}
