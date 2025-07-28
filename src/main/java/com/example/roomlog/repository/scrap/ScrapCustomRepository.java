package com.example.roomlog.repository.scrap;

import java.util.List;
import java.util.Map;

public interface ScrapCustomRepository {
	
	// 커뮤니티 게시글 목록 - 해당 게시글의 스크랩 여부
	Map<Long, Boolean> checkIsScrappedList(List<Long> communityIds, Long userNumber);

	// 커뮤니티 게시글 상세 - 해당 게시글의 스크랩 여부
	Boolean checkIsScrapped(Long userNumber, Long communityId);
	
}
