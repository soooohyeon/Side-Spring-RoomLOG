package com.example.roomlog.service.community;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.CommunityHashtag;
import com.example.roomlog.domain.community.CommunityImg;
import com.example.roomlog.domain.community.Hashtag;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityDTO;
import com.example.roomlog.repository.community.CommunityHashtagRepository;
import com.example.roomlog.repository.community.CommunityImgRepository;
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
	@Autowired
	CommunityImgService communityImgService;
	@Autowired
	CommunityImgRepository communityImgRepository;

	// 커뮤니티 게시글 등록
	public long insertCommunity(CommunityDTO communityDTO, List<MultipartFile> images) throws IOException {
		User user = userRepository.findByUserId(communityDTO.getUserId()).get();
		
		Community community = Community.builder()
				.user(user)
				.communityTitle(communityDTO.getCommunityTitle())
				.communityContent(communityDTO.getCommunityContent())
				.build();
		communityRepository.save(community);
		
		Long communityId = community.getCommunityId();
		
		// 태그 등록 처리
		List<String> tags = communityDTO.getTags();
		if (tags != null && !tags.isEmpty()) {
			for (String tag : tags) {
				Hashtag hashtag = hashtagService.selectHashtag(tag);
				communityHashtagRepository.save(new CommunityHashtag(community, hashtag));
			}
		}
		
		// 이미지 등록 처리
		for (MultipartFile image : images) {
			if (image.isEmpty()) { break; }
			
			CommunityImg communityImg = communityImgService.insertCommunityImg(community, image);
			communityImgRepository.save(communityImg);
		}

		return communityId;
	}
	
	
	

	
}
