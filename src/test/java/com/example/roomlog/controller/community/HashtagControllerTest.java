package com.example.roomlog.controller.community;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.controller.community.HashtagRestController;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class HashtagControllerTest {

	@Autowired
	HashtagRestController hashtagController;

	// 해시태그 목록 (검색 조건 포함)
	@Test
	public void selectAllHashtagsTest() {
		String keyword = "";
		List<String> hashtags = hashtagController.selectAllHashtags(keyword);
	}
	
}
