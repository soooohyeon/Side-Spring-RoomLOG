package com.example.roomlog.repository.community.image;

import java.util.List;

public interface CommunityImgCustomRepository {

	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 이미지 경로 조회
	List<String> selectImgList(long communityId);
	
}
