package com.example.roomlog.repository.scrap;

import java.util.List;
import java.util.Map;

public interface ScrapCustomRepository {
	
	// // 해당 게시글의 스크랩 여부
	public Map<Long, Boolean> checkIsScrapped(List<Long> communityIds, Long userNumber);
	
}
