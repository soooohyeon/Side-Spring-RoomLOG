package com.example.roomlog.domain.community;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table (name = "tbl_community_hashtag")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityHashtag {

	@EmbeddedId
	private CommunityHashtagId communityHashtagId;

    @MapsId(value = "communityId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Community community;
    @MapsId(value = "hashtagId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hashtag_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Hashtag hashtag;
	
	@Builder
	public CommunityHashtag(Community community, Hashtag hashtag) {
		this.communityHashtagId = new CommunityHashtagId(community.getCommunityId(), hashtag.getHashtagId());
		this.community = community;
		this.hashtag = hashtag;
	}
	
}
