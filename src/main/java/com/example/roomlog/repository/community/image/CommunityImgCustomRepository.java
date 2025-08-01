package com.example.roomlog.repository.community.image;

import java.util.List;

import com.example.roomlog.dto.community.image.CommunityImgDTO;

public interface CommunityImgCustomRepository {

	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 이미지 경로 조회
	List<String> selectImgList(long communityId);
	
	// 해당 게시글의 이미지 조회
	List<CommunityImgDTO> findCommunityImg(long communityId);
	
	// 해당 게시글의 이미지 1개씩 조회
	CommunityImgDTO findCommunityImgByImageId(long imageId);

}
