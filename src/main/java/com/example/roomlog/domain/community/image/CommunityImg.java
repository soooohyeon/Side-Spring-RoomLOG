package com.example.roomlog.domain.community.image;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.community.Community;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_community_img")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityImg {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long communityImgId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "communityId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Community community;
	@Column(nullable = false)
	private String communityImgOriginal;
	@Column(unique = true, nullable = false)
	private String communityImgUuid;
	@Column(nullable = false)
	private String communityImgPath;
	
	@Builder
	public CommunityImg(Community community, String communityImgOriginal, String communityImgUuid, String communityImgPath) {
		this.community = community;
		this.communityImgOriginal = communityImgOriginal;
		this.communityImgUuid = communityImgUuid;
		this.communityImgPath = communityImgPath;
	}
	
}
