package com.example.roomlog.service.community;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.Hashtag;
import com.example.roomlog.repository.community.HashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

	@Autowired
	HashtagRepository hashtagRepository;
	
	// 해시태그 목록 (검색 조건 포함)
	public List<String> findByHashtagList(String keyword) {
		List<Hashtag> hashtagDB = hashtagRepository.findByHashtagNameContaining(keyword);
		List<String> hashtags = hashtagDB.stream()
		.map(Hashtag::getHashtagName)
		.collect(Collectors.toList());
		
		return hashtags;
	}
	
}
