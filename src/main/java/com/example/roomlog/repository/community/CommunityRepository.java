package com.example.roomlog.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.dto.page.Criteria;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {
	
	// 게시글 1개 조회
	Community findByCommunityId(Long communityId);
	
	// 게시글 삭제
	@Transactional
	void deleteByCommunityId(Long communityId);
	
}
