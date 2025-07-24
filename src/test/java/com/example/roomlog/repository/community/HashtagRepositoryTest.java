package com.example.roomlog.repository.community;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.Hashtag;
import com.example.roomlog.repository.community.HashtagRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class HashtagRepositoryTest {

	@Autowired
	HashtagRepository hashtagRepository;
	
	// 해시태그 목록 (검색 조건 포함)
	@Test
	public void findByHashtagNameContainingTest() {
		String keyword = "소파";
		List<Hashtag> hashtags = hashtagRepository.findByHashtagNameContaining(keyword);
		
//		for (Hashtag hashtag : hashtags) {
//			log.info("hashtag : {}", hashtag);
//		}
		
		assertNull(hashtags);
	}
	
	// 해시태그 존재 여부 확인
	@Test
	public void findByHashtagNameTest() {
		String tag = "집꾸미기";
		Hashtag hashtag = hashtagRepository.findByHashtagName(tag).get();

		assertNull(hashtag);
	}
	
}
