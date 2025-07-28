package com.example.roomlog.repository.follow;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
	public void checkFollowTest() {
		// given
		long fromUserId = 1;
		long toUserId = 2;
		// when
		long result = followRepository.checkFollow(fromUserId, toUserId);
		// then
		assertEquals(result, 1);
	}
	
}
