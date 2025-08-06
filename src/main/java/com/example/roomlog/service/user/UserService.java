package com.example.roomlog.service.user;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;
import com.example.roomlog.repository.follow.FollowRepository;
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
		}
		profileImgRepository.save(profileImg);
		
		if (pageType.equals("JOIN")) {
			user.saveUserIntro(userIntro);
		} else if (pageType.equals("MYPAGE")) {
			user.updateUserInfo(userNickname, isAgeVisible, userIntro);
		}
		userRepository.save(user);
	}
	
	// 마이페이지 - 유저 정보 출력
	public UserInfoDTO selectUser(long userId) {
		UserInfoDTO user = userRepository.selectUser(userId);
		
		user.setUserAge(AgeUtils.getAgeLabel(user.getIsAgeVisible(), user.getUserBirth()));
		user.setFollowCount(followRepository.countFollow(userId));
		user.setFollowerCount(followRepository.countFollower(userId));
		
		return user;
	}
    
}