package com.example.roomlog.service.community;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.Hashtag;
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
		// given
		String keyword = "소파";
		// when
		List<String> hashtags = hashtagService.findByHashtagList(keyword);
		// then
//		for (String hashtag : hashtags) {
//			log.info("hashtag : {}", hashtag);
//		}
		assertNotNull(hashtags);
	}
	
	// 해시태그 검색하고 없으면 등록
	@Test
	public void insertHashtagTest() {
		// given
		String tag = "집꾸미기";
		// when
		Hashtag hashtag = hashtagService.selectHashtag(tag);
		// then
		assertNotNull(hashtag);
		assertThat(hashtag.getHashtagName()).isEqualTo(tag);
	}
	
}
