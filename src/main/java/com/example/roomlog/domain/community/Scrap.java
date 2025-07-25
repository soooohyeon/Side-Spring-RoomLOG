package com.example.roomlog.domain.community;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.user.User;

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
@Table (name = "tbl_scrap")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Scrap {
	
	@EmbeddedId
	private ScrapId scrapId;

    @MapsId(value = "userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
    @MapsId(value = "communityId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Community community;
    
    @Builder
	public Scrap(User user, Community community) {
		this.scrapId = new ScrapId(user.getUserId(), community.getCommunityId());
		this.user = user;
		this.community = community;
	}
    
}
