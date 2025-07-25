package com.example.roomlog.repository.community.hashtag;

import java.util.List;
import java.util.Map;

public interface HashtagCustomRepository {

	// 커뮤니티 게시글 목록 - 각 게시글의 해시태그
	Map<Long, List<String>> selectListHashtag (List<Long> communityIds);
	
}
