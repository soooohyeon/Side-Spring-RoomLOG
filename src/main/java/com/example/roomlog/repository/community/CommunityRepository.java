package com.example.roomlog.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.dto.page.Criteria;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {
	
	// 게시글 1개 조회
	Community findByCommunityId (Long communityId);
	
}
