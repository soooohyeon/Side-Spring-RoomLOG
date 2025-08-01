package com.example.roomlog.service.scrap;

import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.scrap.Scrap;
import com.example.roomlog.domain.scrap.ScrapId;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.scrap.ScrapRepository;
import com.example.roomlog.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapService {

	private final ScrapRepository scrapRepository;
	private final CommunityRepository communityRepository;
	private final UserRepository userRepository;
	
	// 스크랩 하기
	public void insertScrap(Long communityId, Long userNumber) {
		Community community = communityRepository.findByCommunityId(communityId);
		User user = userRepository.findByUserId(userNumber).get();
		
		Scrap scrap = Scrap.builder()
				.community(community)
				.user(user)
				.build();
		scrapRepository.save(scrap);
	}
	
	// 스크랩 취소
	@Transactional
	public void cancelScrap(Long communityId, Long userNumber) {
		scrapRepository.deleteByScrapId(new ScrapId(userNumber, communityId));
	}

}
