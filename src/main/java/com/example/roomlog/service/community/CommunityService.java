package com.example.roomlog.service.community;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.CommunityHashtag;
import com.example.roomlog.domain.community.Hashtag;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityDTO;
import com.example.roomlog.repository.community.CommunityHashtagRepository;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.user.UserRepository;

@Service
public class CommunityService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	CommunityRepository communityRepository;
	@Autowired
	HashtagService hashtagService;
	@Autowired
	CommunityHashtagRepository communityHashtagRepository;
	
	// 커뮤니티 게시글 등록U
	public long insertCommunity(CommunityDTO communityDTO) {
		User user = userRepository.findByUserId(communityDTO.getUserId()).get();
		
		Community community = Community.builder()
				.user(user)
				.communityTitle(communityDTO.getCommunityTitle())
				.communityContent(communityDTO.getCommunityContent())
				.build();
		communityRepository.save(community);
		
		Long communityId = community.getCommunityId();
		
		List<String> tags = communityDTO.getTags();
		if (tags != null && !tags.isEmpty()) {
			for (String tag : tags) {
				Hashtag hashtag = hashtagService.selectHashtag(tag);
				communityHashtagRepository.save(new CommunityHashtag(community, hashtag));
			}
		}

		return communityId;
	}
	
	
	

	
}
