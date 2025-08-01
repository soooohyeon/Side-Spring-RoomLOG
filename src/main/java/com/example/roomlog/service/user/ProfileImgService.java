package com.example.roomlog.service.user;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.util.ImageUploadUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
public class ProfileImgService {

	private final ProfileImgRepository profileImgRepository;
	private final UserRepository userRepository;
	private final ImageUploadUtils imageUploadUtils;

	// 프로필 사진 등록 또는 수정 전 형식변환하여 ProfileImg 객체에 담아 반환
	public ProfileImg updateProfileImg(User user, MultipartFile image) throws IOException {
		Map<String, Object> img = imageUploadUtils.formatImage("profile", image);
		
		// 썸네일 이미지 별도 저장
        Thumbnails.of((File) img.get("uploadImg"))
        .size(350, 350)
        .toFile(new File((File) img.get("uploadPath"), "th_" + img.get("imgUuid")));

        long profileImgId = profileImgRepository.findByUser(user)
        		.map(ProfileImg::getProfileImgId)
        		.orElse(-1L);
        ProfileImg profileImg = null;
		
        if (profileImgId < 0) {
        	profileImg = ProfileImg.builder()
        		.user(user)
	        	.profileImgOriginal((String)img.get("originalImgName"))
	        	.profileImgUuid((String) img.get("imgUuid"))
	        	.profileImgPath("upload/" + img.get("setfileDir"))
	        	.build();
        } else {
        	profileImg = profileImgRepository.findByProfileImgId(profileImgId).get();
        	profileImg.updateProfileImg(img.get("uuid").toString(), (String)img.get("originalImgName"), (String)img.get("setfileDir"));
        }
		
		return profileImg;
	}
	
}
