package com.example.roomlog.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.CommunityImg;

@Repository
public interface CommunityImgRepository extends JpaRepository<CommunityImg, Long> {
	
	// 게시글 이미지 조회
	
	
}
