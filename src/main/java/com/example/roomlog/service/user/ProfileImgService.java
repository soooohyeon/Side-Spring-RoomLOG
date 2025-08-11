package com.example.roomlog.service.user;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.image.CommunityImgDTO;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.util.ImageUploadUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@Service
@RequiredArgsConstructor
public class ProfileImgService {

	private final ProfileImgRepository profileImgRepository;
	private final ImageUploadUtils imageUploadUtils;
	
	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;

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
        	// 회원가입 또는 기존 프로필 이미지가 없을 경우 새로 등록
        	profileImg = ProfileImg.builder()
        		.user(user)
	        	.profileImgOriginal((String)img.get("originalImgName"))
	        	.profileImgUuid((String) img.get("imgUuid"))
	        	.profileImgPath((String) img.get("setfileDir"))
	        	.build();
        } else {
        	// 기존 프로필 이미지 수정할 경우
        	profileImg = profileImgRepository.findByProfileImgId(profileImgId).get();
        	deleteProfileImgFile(profileImg);
        	
        	profileImg.updateProfileImg(img.get("imgUuid").toString(), (String)img.get("originalImgName"), (String)img.get("setfileDir"));
        }
		
		return profileImg;
	}
	
	// 폴더에 저장된 이미지 삭제
	public void deleteProfileImgFile(ProfileImg img) {
		File image = new File(fileDir, img.getProfileImgPath() + "/" + img.getProfileImgUuid());
		File thumbnail = new File(fileDir, img.getProfileImgPath() + "/th_" + img.getProfileImgUuid());
		
		if (image.exists()) {
			image.delete();
		}
		if (thumbnail.exists()) {
			thumbnail.delete();
		}
	}
	
}
