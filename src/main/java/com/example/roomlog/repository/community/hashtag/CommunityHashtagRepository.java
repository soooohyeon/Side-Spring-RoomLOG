package com.example.roomlog.repository.community.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.hashtag.CommunityHashtag;
import com.example.roomlog.domain.community.hashtag.CommunityHashtagId;

public interface CommunityHashtagRepository extends JpaRepository<CommunityHashtag, CommunityHashtagId> {

	// 해당 게시글의 해시태그 삭제
	@Modifying
	@Transactional
	@Query("DELETE FROM CommunityHashtag ch " +
			"	WHERE ch.community.communityId = :communityId")
	void deleteByCommunityId(long communityId);
	
}
