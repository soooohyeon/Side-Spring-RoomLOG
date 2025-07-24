package com.example.roomlog.service.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.repository.user.SocialTypeRepository;
import com.example.roomlog.repository.user.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	SocialTypeRepository socialTypeRepository;
	@Autowired
	ProfileImgService profileImgService;
	@Autowired
	ProfileImgRepository profileImgRepository;

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
	
	// 프로필 사진, 닉네임, 한 줄 소개, 나이 공개 여부 수정 (회원가입시 선택정보 입력, 마이페이지 수정)
	public void updateUserInfo(UserDTO userDTO, MultipartFile image, String pageType) throws IOException {
		String userNickname = userDTO.getUserNickname();
		int isAgeVisible = userDTO.getIsAgeVisible();
		String userIntro = userDTO.getUserIntro();
		
		User user = userRepository.findByUserId(userDTO.getUserId()).get();
		if (image != null && !image.isEmpty()) {
			ProfileImg profileImg = profileImgService.updateProfileImg(user, image);
			profileImgRepository.save(profileImg);
		}
		
		if (pageType.equals("JOIN")) {
			user.saveUserIntro(userIntro);
		} else if (pageType.equals("MYPAGE")) {
			user.updateUserInfo(userNickname, isAgeVisible, userIntro);
		}
		userRepository.save(user);
	}
    
}