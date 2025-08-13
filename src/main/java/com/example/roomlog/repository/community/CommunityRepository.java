package com.example.roomlog.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {
	
	// 마이페이지 - 해당 유저가 작성한 커뮤니티 게시글 개수
	@Query("SELECT COUNT(c) FROM Community c " +
			"	WHERE c.user.userId = :userId")
	int countListByUser(long userId);
	
	// 게시글 1개 조회
	Community findByCommunityId(long communityId);
	
	// 게시글 삭제
	@Transactional
	void deleteByCommunityId(long communityId);
	
	// 회원 탈퇴시 해당 회원이 작성한 게시글 번호만 조회
	@Query("SELECT c.communityId FROM Community c WHERE c.user.userId = :userId")
	List<Long> findAllCommunityByUserId (long userId);
	
}
