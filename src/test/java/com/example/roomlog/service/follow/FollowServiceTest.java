package com.example.roomlog.service.follow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.roomlog.domain.follow.Follow;
import com.example.roomlog.repository.follow.FollowRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class FollowServiceTest {

	@Autowired
	FollowService followService;
	@Autowired
	FollowRepository followRepository;

	// 팔로우
	@Test
	public void insertFollowTest() {
		// given
		long fromUserId = 1;
		long toUserId = 2;
		// when
		followService.insertFollow(fromUserId, toUserId);
		// then
		assertNotNull(followRepository.checkFollow(fromUserId, toUserId));
	}
	
	// 팔로우 취소
	@Test
	public void cancelFollow() {
		// given
		long fromUserId = 1;
		long toUserId = 2;
		// when
		followService.cancelFollow(fromUserId, toUserId);
		// then
		assertNull(followRepository.checkFollow(fromUserId, toUserId));
	}
	
}
