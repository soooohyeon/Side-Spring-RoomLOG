package com.example.roomlog.repository.community.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.CommunityImg;

@Repository
public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {
	
	// 해당 게시글의 대표 이미지 조회
	@Query("SELECT img FROM CommunityImg img " +
			"	WHERE img.community.communityId IN :communityIds AND " +
			"	img.communityImgId IN (" +
			"   	SELECT MIN(img2.communityImgId) " +
			"   	FROM CommunityImg img2 " +
			"   	WHERE img2.community.communityId IN :communityIds " +
			"   	GROUP BY img2.community.communityId)")
	List<CommunityImg> findFirstImagesByCommunityIds(List<Long> communityIds);
	
}