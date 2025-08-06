package com.example.roomlog.repository.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.follow.Follow;

import jakarta.transaction.Transactional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

	// 팔로우 여부 확인
	@Query("SELECT COUNT(f) FROM Follow f " +
			"	WHERE f.fromUser.userId = :fromUserId " +
			"		AND f.toUser.userId = :toUserId")
	long checkFollow(long fromUserId, long toUserId);
	
	// 팔로우 해제
	@Modifying
	@Transactional
	@Query("DELETE FROM Follow f " +
			"	WHERE f.fromUser.userId = :fromUserId " +
			"		AND f.toUser.userId = :toUserId")
	void cancelFollow(long fromUserId, long toUserId);
	
	// 내가 팔로우한 수
	@Query("SELECT COUNT(f) FROM Follow f " +
			"	WHERE f.fromUser.userId = :userId")
	int countFollow(long userId);
	
	// 나를 팔로우한 팔로워 수
	@Query("SELECT COUNT(f) FROM Follow f " +
			"	WHERE f.toUser.userId = :userId")
	int countFollower(long userId);
	
}
