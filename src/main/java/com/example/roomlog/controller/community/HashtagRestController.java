package com.example.roomlog.controller.community;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.community.HashtagService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HashtagRestController {
	
	private final HashtagService hashtagService;

	// 해시태그 목록 (검색 조건 포함)
	@GetMapping("/hashtag")
	public List<String> selectAllHashtags(@RequestParam String keyword) {
		keyword = keyword == null ? "" : keyword;
		List<String> hashtags = hashtagService.findByHashtagList(keyword);
		
		return hashtags;
	}
	
}
