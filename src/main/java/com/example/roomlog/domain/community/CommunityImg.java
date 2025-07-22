package com.example.roomlog.domain.community;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "tbl_community_img")
@Getter @ToString
public class CommunityImg {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long communityImgId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "communityId", nullable = false)
	private Community community;
	@Column(nullable = false)
	private String communityImgOriginal;
	@Column(unique = true, nullable = false)
	private String communityImgUuid;
	
	@Builder
	public CommunityImg(Community community, String communityImgOriginal, String communityImgUuid) {
		this.community = community;
		this.communityImgOriginal = communityImgOriginal;
		this.communityImgUuid = communityImgUuid;
	}
	
}
