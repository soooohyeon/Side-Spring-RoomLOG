package com.example.roomlog.repository.community;

import java.util.List;

import com.example.roomlog.dto.community.CommunityEditDTO;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.page.Criteria;

public interface CommunityCustomRepository {

	// 커뮤니티 게시글 목록
	List<CommunityListDTO> selectListWithPaging(Criteria criteria);

	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	long countSearchResult(Criteria criteria);
	
	// 커뮤니티 게시글 상세 정보
	CommunityViewDTO selectViewOne(long communityId);
	
	// 커뮤니티 수정 전 게시글 정보 보기
	CommunityEditDTO selectViewOneBeforeEdit(long communityId);
}
