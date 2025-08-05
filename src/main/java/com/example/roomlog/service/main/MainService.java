package com.example.roomlog.service.main;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.image.CommunityImg;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.image.CommunityImgRepository;
import com.example.roomlog.repository.follow.FollowRepository;
import com.example.roomlog.repository.scrap.ScrapRepository;
import com.example.roomlog.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainService {

	private final CommunityRepository communityRepository;
	private final CommunityImgRepository communityImgRepository;
	private final ScrapRepository scrapRepository;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	
	// 팔로우 많은 순 상위 4명의 유저
	public List<UserDTO> selectFollowRankList(long userId) {
		List<UserDTO> lists = userRepository.selectFollowRankingList();
		
		// 팔로우 여부
		for (UserDTO list : lists) {
			long followCount = followRepository.checkFollow(userId, list.getUserId());
			list.setFollowed(followCount != 0);
		}
		
		return lists;
	}
		
	// 커뮤니티 게시글 스크랩 순 상위 3개
	public List<CommunityListDTO> selectScrapRankList(long userId) {
		List<CommunityListDTO> lists = communityRepository.selectScrapRankingList();
		List<Long> communityIds = lists.stream()
			.map(CommunityListDTO::getCommunityId)
			.collect(Collectors.toList());

		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		Map<Long, Boolean> checkIsScrappedMap = scrapRepository.checkIsScrappedList(communityIds, userId);
		
		for (CommunityListDTO list : lists) {
			long commuId = list.getCommunityId();

			// 각 게시글 대표 이미지 출력
			Map<Long, CommunityImg> imageMap = images.stream()
				.collect(Collectors.toMap(
					img -> img.getCommunity().getCommunityId(),
					Function.identity()
				));
		    
			CommunityImg commuImg = imageMap.get(commuId);
			if (commuImg != null) {
			    list.setCommunityImgPath(commuImg.getCommunityImgPath());
			    list.setCommunityImgUuid(commuImg.getCommunityImgUuid());
			    list.setCheckCommunityImg(true);
			}
			
			// 스크랩 여부
			list.setScrapped(checkIsScrappedMap.getOrDefault(commuId, false));
		}
		
		return lists;
	}
	
}
