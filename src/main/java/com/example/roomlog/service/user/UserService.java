package com.example.roomlog.service.user;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.roomlog.controller.AdminController;
import com.example.roomlog.controller.MainController;
import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.repository.user.SocialTypeRepository;
import com.example.roomlog.repository.user.UserRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	SocialTypeRepository socialTypeRepository;
	@Autowired
	ProfileImgRepository profileImgRepository;
	
	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;

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
		
		System.out.println(userIntro);
		
		User user = userRepository.findByUserId(userDTO.getUserId()).get();
		long profileImgId = user.getProfileImg() == null ? -1 : user.getProfileImg().getProfileImgId();
		
		if (!image.isEmpty()) {
			ProfileImg profileImg = updateProfileImg(profileImgId, image);
			profileImgRepository.save(profileImg);
			user.updateProfileImg(profileImg);
		}
		
		if (pageType.equals("JOIN")) {
			user.saveUserIntro(userIntro);
		} else if (pageType.equals("MYPAGE")) {
			user.updateUserInfo(userNickname, isAgeVisible, userIntro);
		}
		userRepository.save(user);
	}
	
	// 프로필 사진 등록 또는 수정
	public ProfileImg updateProfileImg(long profileImgId, MultipartFile image) throws IOException {
		String originalImgName = image.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String systemName = uuid.toString() + "_" + originalImgName;
		
		String setfileDir = "profile/" + getUploadDate();
		File uploadPath = new File(fileDir, setfileDir);
		
		// 경로가 존재하지 않는다면 필요한 모든 폴더 생성
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		File uploadImg = new File(uploadPath, systemName);
		
		// 매개변수로 받은 Multipart 객체에 담긴 이미지를 우리가 만든 경로와 이름으로 저장
		image.transferTo(uploadImg);
		
		// 썸네일 이미지 별도 저장
        Thumbnails.of(uploadImg)
        .size(350, 350)
        .toFile(new File(uploadPath, "th_" + systemName));
        
        ProfileImg profileImg = null;
		
        if (profileImgId < 0) {
        	profileImg = ProfileImg.builder()
        			.profileImgOriginal(originalImgName)
        			.profileImgUuid(uuid.toString())
        			.profileImgPath(setfileDir)
        			.build();
        } else {
        	profileImg = profileImgRepository.findByProfileImgId(profileImgId).get();
        	profileImg.updateProfileImg(uuid.toString(), originalImgName, setfileDir);
        }
		
		return profileImg;
	}

	// 이미지 업로드시 업로드 날짜별 이미지 저장
    private String getUploadDate(){
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }
    
}