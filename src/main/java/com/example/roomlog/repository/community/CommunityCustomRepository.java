package com.example.roomlog.repository.community;

import java.util.List;

import com.example.roomlog.dto.community.CommunityEditDTO;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.page.Criteria;

public interface CommunityCustomRepository {

	// 메인 - 커뮤니티 스크랩 순 게시글 상위 3개
	List<CommunityListDTO> selectScrapRankingList();
	
	// 마이페이지 - 해당 유저가 작성한 커뮤니티 게시글
	List<CommunityListDTO> selectListByUser(long userId, Criteria criteria);
	
	// 마이페이지 - 해당 유저가 스크랩한 커뮤니티 게시글
	List<CommunityListDTO> selectScrapListByUser(long userId, Criteria criteria);
	
	// 커뮤니티 게시글 목록
	List<CommunityListDTO> selectListWithPaging(Criteria criteria);

	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	long countSearchResult(Criteria criteria);
	
	// 커뮤니티 게시글 상세 정보
	CommunityViewDTO selectViewOne(long communityId);
	
	// 커뮤니티 수정 전 게시글 정보 보기
	CommunityEditDTO selectViewOneBeforeEdit(long communityId);
}
