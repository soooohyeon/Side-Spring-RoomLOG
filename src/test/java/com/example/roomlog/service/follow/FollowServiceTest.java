package com.example.roomlog.service.follow;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.roomlog.dto.follow.FollowDTO;
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
	
	// 내가 팔로우한 유저 목록
	@Test
	public void selectFollowListTest() {
		// given
		long userId = 1;
		String keyword = "";
		// when
		List<FollowDTO> lists = followService.selectFollowList(userId, keyword);
		// then
		assertNotNull(lists);
		for (FollowDTO list : lists) {
			log.info("팔로우 목록 : " + list);
		}
	}
	
	// 나를 팔로우한 팔로워 목록
	@Test
	public void selectFollowerListTest() {
		// given
		long userId = 1;
		String keyword = "";
		// when
		List<FollowDTO> lists = followService.selectFollowerList(userId, keyword);
		// then
		assertNotNull(lists);
		for (FollowDTO list : lists) {
			log.info("팔로워 목록 : " + list);
		}
	}
	
}
