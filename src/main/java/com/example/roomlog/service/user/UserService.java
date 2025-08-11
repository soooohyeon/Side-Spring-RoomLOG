package com.example.roomlog.service.user;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.community.image.CommunityImg;
import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.hashtag.HashtagRepository;
import com.example.roomlog.repository.community.image.CommunityImgRepository;
import com.example.roomlog.repository.follow.FollowRepository;
import com.example.roomlog.repository.scrap.ScrapRepository;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.repository.user.SocialTypeRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.util.AgeUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final SocialTypeRepository socialTypeRepository;
	private final ProfileImgService profileImgService;
	private final ProfileImgRepository profileImgRepository;
	private final FollowRepository followRepository;
	private final CommunityRepository communityRepository;
	private final CommunityImgRepository communityImgRepository;
	private final HashtagRepository hashtagRepository;
	private final ScrapRepository scrapRepository;

	// 회원가입 - 필수 정보
	public int insertUser(UserDTO userDTO, String socialTypeName) {
		SocialType socialType = socialTypeRepository.findBySocialTypeName(socialTypeName);
		
		User user = User.builder()
				.socialType(socialType)
				.userEmail(userDTO.getUserEmail())
				.userNickname(userDTO.getUserNickname())
				.userBirth(userDTO.getUserBirth())
				.isAgeVisible(userDTO.getIsAgeVisible())
				.build();

		userRepository.save(user);
		return (int) user.getUserId();
	}
	
	// 닉네임 중복 체크
	public boolean checkNickname(String nickname) {
		return userRepository.existsByUserNickname(nickname);
	}

	// 회원가입시 선택정보 입력, 마이페이지 수정 - 프로필 사진, 닉네임, 한 줄 소개, 나이 공개 여부 수정
	public void updateUserInfo(UserDTO userDTO, MultipartFile image, String pageType) throws IOException {
		String userNickname = userDTO.getUserNickname();
		int isAgeVisible = userDTO.getIsAgeVisible();
		String userIntro = userDTO.getUserIntro();
		
		ProfileImg profileImg = null;
		User user = userRepository.findByUserId(userDTO.getUserId()).get();
		if (image != null && !image.isEmpty()) {
			profileImg = profileImgService.updateProfileImg(user, image);
			profileImgRepository.save(profileImg);
		}
		
		if (pageType.equals("JOIN")) {
			user.saveUserIntro(userIntro);
		} else if (pageType.equals("MYPAGE")) {
			user.updateUserInfo(userNickname, isAgeVisible, userIntro);
		}
		userRepository.save(user);
	}
	
	// 마이페이지(메인) - 유저 정보 출력
	public UserInfoDTO selectUser(long userId) {
		UserInfoDTO user = userRepository.selectUser(userId);
		
		user.setUserAge(AgeUtils.getAgeLabel(user.getIsAgeVisible(), user.getUserBirth()));
		user.setFollowCount(followRepository.countFollow(userId));
		user.setFollowerCount(followRepository.countFollower(userId));
		
		return user;
	}
	
	// 마이페이지(메인) - 해당 유저가 작성한 커뮤니티 게시글 개수
	public int countList(long userId) {
		return communityRepository.countListByUser(userId);
	}
	
	// 마이페이지(메인) - 해당 유저가 작성한 커뮤니티 게시글
	public List<CommunityListDTO> selectList(long userId, Criteria criteria) {
		List<CommunityListDTO> lists = communityRepository.selectListByUser(userId, criteria);
		List<Long> communityIds = lists.stream()
			.map(CommunityListDTO::getCommunityId)
			.collect(Collectors.toList());
		
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		Map<Long, List<String>> hashtagMap = hashtagRepository.selectAllHashtagList(communityIds);

		for(CommunityListDTO list : lists) {
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
			
			// 해시태그
			list.setTags(hashtagMap.getOrDefault(commuId, List.of()));
		}
		
		return lists;
	}
	
	// 마이페이지(메인) - 해당 유저가 스크랩한 커뮤니티 게시글 개수
	public int countScrapList(long userId) {
		return scrapRepository.countScrapListByUser(userId);
	}
	
	// 마이페이지(메인) - 해당 유저가 스크랩한 커뮤니티 게시글
	public List<CommunityListDTO> selectScrapList(long userId, Criteria criteria) {
		List<CommunityListDTO> lists = communityRepository.selectScrapListByUser(userId, criteria);
		List<Long> communityIds = lists.stream()
			.map(CommunityListDTO::getCommunityId)
			.collect(Collectors.toList());
		
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
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
			
			// 해시태그
			list.setTags(hashtagMap.getOrDefault(commuId, List.of()));
		}
		
		return lists;
	}
	
	// 마이페이지(설정) - 수정할 유저 정보 출력
	public UserInfoDTO selectEditUser(long userId) {
		UserInfoDTO user = userRepository.selectEditUser(userId);
		String birth = user.getUserBirth().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
		user.setFormatBirth(birth);
		
		return user;
	}
	
}