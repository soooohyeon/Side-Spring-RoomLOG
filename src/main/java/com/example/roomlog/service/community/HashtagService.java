package com.example.roomlog.service.community;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.hashtag.Hashtag;
import com.example.roomlog.repository.community.hashtag.CommunityHashtagRepository;
import com.example.roomlog.repository.community.hashtag.HashtagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HashtagService {

	private final HashtagRepository hashtagRepository;
	private final CommunityHashtagRepository communityHashtagRepository;
	
	// 해시태그 목록 (검색 조건 포함)
	public List<String> findByHashtagList(String keyword) {
		List<Hashtag> hashtagDB = hashtagRepository.findByHashtagNameContaining(keyword);
		List<String> hashtags = hashtagDB.stream()
		.map(Hashtag::getHashtagName)
		.collect(Collectors.toList());
		
		return hashtags;
	}
	
	// 해시태그 검색하고 없으면 등록
	public Hashtag selectHashtag(String hashtag) {
		return hashtagRepository.findByHashtagName(hashtag)
				.orElseGet(() -> hashtagRepository.save(new Hashtag(hashtag)));
	}
	
	// 해당 게시글의 해시태그 조회
	public List<String> selectAllHashtagsByEdit(long communityId) {
		return hashtagRepository.selectHashtagList(communityId);
	}
	
	// 해당 게시글의 해시태그 삭제
	public void deleteHashtag(long communityId) {
		communityHashtagRepository.deleteByCommunityId(communityId);
	}
	
}
