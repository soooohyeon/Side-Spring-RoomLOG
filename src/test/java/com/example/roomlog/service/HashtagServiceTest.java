package com.example.roomlog.service;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.service.community.HashtagService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class HashtagServiceTest {

	@Autowired
	HashtagService hashtagService;

	// 해시태그 목록 (검색 조건 포함)
	@Test
	public void findByHashtagListTest() {
		String keyword = "소파";
		List<String> hashtags = hashtagService.findByHashtagList(keyword);

//		for (String hashtag : hashtags) {
//			log.info("hashtag : {}", hashtag);
//		}
		assertNull(hashtags);
	}
	
}
