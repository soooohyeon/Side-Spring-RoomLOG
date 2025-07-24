package com.example.roomlog.repository.community;

import java.util.List;

import com.example.roomlog.dto.community.CommunityDTO;

public interface CommunityCustomRepository {

	// 커뮤니티 게시글 목록
	public List<CommunityDTO> selectListAll();
	
}
