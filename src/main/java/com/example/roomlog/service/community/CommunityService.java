package com.example.roomlog.service.community;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.domain.community.hashtag.CommunityHashtag;
import com.example.roomlog.domain.community.hashtag.Hashtag;
import com.example.roomlog.domain.community.image.CommunityImg;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityEditDTO;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityRegistDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.community.image.CommunityImgDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.hashtag.CommunityHashtagRepository;
import com.example.roomlog.repository.community.hashtag.HashtagRepository;
import com.example.roomlog.repository.community.image.CommunityImgRepository;
import com.example.roomlog.repository.follow.FollowRepository;
import com.example.roomlog.repository.scrap.ScrapRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.util.AgeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityService {

	private final UserRepository userRepository;
	private final CommunityRepository communityRepository;
	private final HashtagService hashtagService;
	private final CommunityHashtagRepository communityHashtagRepository;
	private final CommunityImgService communityImgService;
	private final CommunityImgRepository communityImgRepository;
	private final ScrapRepository scrapRepository;
	private final HashtagRepository hashtagRepository;
	private final FollowRepository followRepository;

	// 커뮤니티 게시글 총 개수
	public int countAllCommunity() {
		return (int) communityRepository.count();
	}
	
	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	public int countSearchResult(Criteria criteria) {
		return (int) communityRepository.countSearchResult(criteria);
	}
	
	// 커뮤니티 게시글 목록
	public List<CommunityListDTO> selectListAll(long userId, Criteria criteria) {
		List<CommunityListDTO> lists = communityRepository.selectListWithPaging(criteria);
		List<Long> communityIds = lists.stream()
			.map(CommunityListDTO::getCommunityId)
			.collect(Collectors.toList());
		
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		Map<Long, Boolean> checkIsScrappedMap = scrapRepository.checkIsScrappedList(communityIds, userId);
		Map<Long, List<String>> hashtagMap = hashtagRepository.selectAllHashtagList(communityIds);
		
		for (CommunityListDTO list : lists) {
			long commuId = list.getCommunityId();

			// 나이대
			list.setUserAge(AgeUtils.getAgeLabel(list.getIsAgeVisible(), list.getUserBirth()));
			
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
	
	// 커뮤니티 상세 게시글 정보
	public CommunityViewDTO selectViewOne(long userId, long communityId) {
		CommunityViewDTO post = communityRepository.selectViewOne(communityId);

		// 나이대
		post.setUserAge(AgeUtils.getAgeLabel(post.getIsAgeVisible(), post.getUserBirth()));
		
		post.setImages(communityImgRepository.selectImgList(communityId));
		post.setScrapped(scrapRepository.checkIsScrapped(userId, communityId));
		post.setFollowed(followRepository.checkFollow(userId, post.getUserId()) == 1);
		post.setTags(hashtagRepository.selectHashtagList(communityId));
		 
		return post;
	}
	
	// 커뮤니티 게시글 등록
	public long insertCommunity(CommunityRegistDTO communityRegistDTO, List<MultipartFile> images) throws IOException {
		User user = userRepository.findByUserId(communityRegistDTO.getUserId()).get();
		
		Community community = Community.builder()
				.user(user)
				.communityTitle(communityRegistDTO.getCommunityTitle())
				.communityContent(communityRegistDTO.getCommunityContent())
				.build();
		communityRepository.save(community);
		
		Long communityId = community.getCommunityId();
		
		// 태그 등록 처리
		List<String> tags = communityRegistDTO.getTags();
		if (tags != null) {
			for (String tag : tags) {
				Hashtag hashtag = hashtagService.selectHashtag(tag);
				communityHashtagRepository.save(new CommunityHashtag(community, hashtag));
			}
		}
		
		// 이미지 등록 처리
		for (MultipartFile image : images) {
			if (image.isEmpty()) { break; }
			
			CommunityImg communityImg = communityImgService.formatCommunityImg(community, image);
			communityImgRepository.save(communityImg);
		}

		return communityId;
	}

	// 커뮤니티 수정 전 게시글 정보 보기
	public CommunityEditDTO selectViewOneBeforeEdit (long communityId) {
		CommunityEditDTO post = communityRepository.selectViewOneBeforeEdit(communityId);
		
		post.setCommunityImages(communityImgRepository.findCommunityImg(communityId));
		post.setTags(hashtagRepository.selectHashtagList(communityId));
		 
		return post;
	}
	
	// 커뮤니티 게시글 수정
	public void editCommunity(CommunityEditDTO communityEditDTO, List<MultipartFile> images) throws IOException {
		Community community = communityRepository.findByCommunityId(communityEditDTO.getCommunityId());
		community.updateCommunity(communityEditDTO.getCommunityTitle(), communityEditDTO.getCommunityContent());
		
		communityRepository.save(community);

		// 기존 DB에 등록된 해시태그가 있다면 삭제
		List<String> dbTags = hashtagService.selectAllHashtagsByEdit(communityEditDTO.getCommunityId());
		if (dbTags != null && !dbTags.isEmpty()) {
			hashtagService.deleteHashtag(communityEditDTO.getCommunityId());
		}
		
		// 변경된 태그로 등록
		List<String> tags = communityEditDTO.getTags();
		if (tags != null) {
			for (String tag : tags) {
				Hashtag hashtag = hashtagService.selectHashtag(tag);
				communityHashtagRepository.save(new CommunityHashtag(community, hashtag));
			}
		}
		
		// 리스트에 담긴 삭제한 이미지 번호를 사용하여 이미지 삭제
		List<String> deleteImgIds = communityEditDTO.getDeleteImgId();
		if (deleteImgIds != null) {
			for (String imgId : deleteImgIds) {
				communityImgService.deleteCommunityImg(Long.parseLong(imgId));
			}
		}
		
		// 추가로 등록한 이미지 저장
		for (MultipartFile image : images) {
			if (image.isEmpty()) { break; }
			
			CommunityImg communityImg = communityImgService.formatCommunityImg(community, image);
			communityImgRepository.save(communityImg);
		}
	}
	
	// 게시글 삭제
	public void deleteCommunity(long communityId) {
		List<CommunityImgDTO> images = communityImgRepository.findCommunityImg(communityId);
		
		// 실제 폴더 경로에서 이미지 삭제
		for (CommunityImgDTO img : images) {
			communityImgService.deleteCommunityImgFile(img);
		}
		communityRepository.deleteByCommunityId(communityId);
	}
	
}
