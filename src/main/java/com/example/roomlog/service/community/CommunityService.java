package com.example.roomlog.service.community;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.CommunityHashtag;
import com.example.roomlog.domain.community.CommunityImg;
import com.example.roomlog.domain.community.Hashtag;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.hashtag.CommunityHashtagRepository;
import com.example.roomlog.repository.community.hashtag.HashtagRepository;
import com.example.roomlog.repository.community.image.CommunityImgRepository;
import com.example.roomlog.repository.scrap.ScrapRepository;
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
	@Autowired
	ScrapRepository scrapRepository;
	@Autowired
	HashtagRepository hashtagRepository;

	// 커뮤니티 게시글 총 개수
	public int countAllCommunity() {
		return (int) communityRepository.count();
	}
	
	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	public int countSearchResult(Criteria criteria) {
		return (int) communityRepository.countSearchResult(criteria);
	}
	
	// 커뮤니티 게시글 목록
	public List<CommunityListDTO> selectListAll(long userNumber, Criteria criteria) {
		List<CommunityListDTO> lists = communityRepository.selectListWithPaging(criteria);
		List<Long> communityIds = lists.stream()
			.map(CommunityListDTO::getCommunityId)
			.collect(Collectors.toList());
		
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		Map<Long, Boolean> checkIsScrappedMap = scrapRepository.checkIsScrapped(communityIds, userNumber);
		Map<Long, List<String>> hashtagMap = hashtagRepository.selectListHashtag(communityIds);
		
		for (CommunityListDTO list : lists) {
			long commuId = list.getCommunityId();

			// 나이대
			if (list.getIsAgeVisible() == 1) {
				list.setUserAge(getAgeLabel(list.getUserBirth()));
			}
			
			// 프로필 등록이 안된 경우 기본 이미지 출력
			if (list.getProfileImgUuid() == null) {
				list.setProfileImgPath(("image/layout"));
				list.setProfileImgUuid("profile_img_basic.png");
			}
			
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
			
			// 스크랩 여부 및 해시태그
			list.setScrapped(checkIsScrappedMap.getOrDefault(commuId, false));
			list.setTags(hashtagMap.getOrDefault(commuId, List.of()));
		}
		
		return lists;
	}
	
	// 나이대 계산
	public String getAgeLabel(LocalDate birth) {
	    if (birth == null) return "비공개";

	    int age = Period.between(birth, LocalDate.now()).getYears();
	    int ageGroup = (age / 10) * 10;
	    return ageGroup + "대";
	}
	
	// 커뮤니티 게시글 등록
	public long insertCommunity(CommunityListDTO communityListDTO, List<MultipartFile> images) throws IOException {
		User user = userRepository.findByUserId(communityListDTO.getUserId()).get();
		
		Community community = Community.builder()
				.user(user)
				.communityTitle(communityListDTO.getCommunityTitle())
				.communityContent(communityListDTO.getCommunityContent())
				.build();
		communityRepository.save(community);
		
		Long communityId = community.getCommunityId();
		
		// 태그 등록 처리
		List<String> tags = communityListDTO.getTags();
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
