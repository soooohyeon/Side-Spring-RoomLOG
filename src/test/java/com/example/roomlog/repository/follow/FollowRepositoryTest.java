package com.example.roomlog.repository.follow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class FollowRepositoryTest {

	@Autowired
	FollowRepository followRepository;
	
	// 팔로우 여부 확인
	@Test
	public void checkFollowTestTest() {
		// given
		long fromUserId = 1;
		long toUserId = 2;
		// when
		long result = followRepository.checkFollow(fromUserId, toUserId);
		// then
		assertEquals(result, 1);
	}

	// 팔로우 취소
	@Test
	public void cancelFollowTest() {
		// given
		long fromUserId = 1;
		long toUserId = 2;
		// when
		followRepository.cancelFollow(fromUserId, toUserId);
		// then
		assertNull(followRepository.checkFollow(fromUserId, toUserId));
	}
	
	// 내가 팔로우한 수
	@Test
	public void countFollowTest() {
		// given
		long userId = 1;
		// when
		int count = followRepository.countFollow(userId);
		// then
		log.info("내가 팔로우한 수 : " + count);
	}
	
	// 나를 팔로우한 팔로워 수
	@Test
	public void countFollowerTest() {
		// given
		long userId = 1;
		// when
		int count = followRepository.countFollower(userId);
		// then
		log.info("나를 팔로우한 팔로워 수 : " + count);
	}
	
}
