package com.example.roomlog.repository.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.hashtag.Hashtag;
import com.example.roomlog.repository.community.hashtag.CommunityHashtagRepository;
import com.example.roomlog.repository.community.hashtag.HashtagRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class HashtagRepositoryTest {

	@Autowired
	HashtagRepository hashtagRepository;
	@Autowired
	CommunityHashtagRepository communityHashtagRepository;
	
	// 해시태그 목록 (검색 조건 포함)
	@Test
	public void findByHashtagNameContainingTest() {
		// given
		String keyword = "소파";
		// when
		List<Hashtag> hashtags = hashtagRepository.findByHashtagNameContaining(keyword);
		// then
//		for (Hashtag hashtag : hashtags) {
//			log.info("hashtag : ", hashtag);
//		}
		assertNull(hashtags);
	}
	
	// 해시태그 존재 여부 확인
	@Test
	public void findByHashtagNameTest() {
		// given
		String tag = "집꾸미기";
		// when
		Optional<Hashtag> hashtag = hashtagRepository.findByHashtagName(tag);
		// then
		assertTrue(hashtag.isPresent(), "해시태그가 존재하지 않음");
	}

	// 커뮤니티 게시글 목록 - 각 게시글의 해시태그
	@Test
	public void selectListHashtagTest() {
		// given
		List<Long> communityIds = List.of(9L, 17L, 20L, 21L, 22L, 23L, 24L);
		// when
		Map<Long, List<String>> lists = hashtagRepository.selectAllHashtagList(communityIds);
		// then
		assertNotNull(lists);
	}
	
	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 해시태그 조회
	@Test
	public void selectImgListTest() {
		// given
		long communityId = 21;
		// when
		List<String> tags = hashtagRepository.selectHashtagList(communityId);
		// then
		for (String tag : tags) {
			log.info(tag);
		}
		assertNotNull(tags);
	}

	// 해당 게시글의 해시태그 삭제
	@Test
	public void deleteByCommunityIdTest() {
		// given
		long communityId = 18;
		// when
		communityHashtagRepository.deleteByCommunityId(communityId);
		// then
		List<String> tags = hashtagRepository.selectHashtagList(communityId);
		assertNull("삭제됨 ? : " + tags);
	}
	
}
