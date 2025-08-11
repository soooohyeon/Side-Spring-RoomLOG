package com.example.roomlog.repository.community.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.image.CommunityImg;

@Repository
public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long>, CommunityImgCustomRepository {
	
	// 커뮤니티 게시글 목록 - 해당 게시글의 대표 이미지 조회
	@Query("SELECT img FROM CommunityImg img " +
			"	WHERE img.community.communityId IN :communityIds AND " +
			"	img.communityImgId IN (" +
			"   	SELECT MIN(img2.communityImgId) " +
			"   	FROM CommunityImg img2 " +
			"   	WHERE img2.community.communityId IN :communityIds " +
			"   	GROUP BY img2.community.communityId)")
	List<CommunityImg> findFirstImagesByCommunityIds(List<Long> communityIds);
	
	// 해당 게시글의 이미지 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM CommunityImg img " +
			"	WHERE img.communityImgId = :imageId")
	void deleteByCommunityId(long imageId);
	
}