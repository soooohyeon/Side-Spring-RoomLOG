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

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.repository.user.ProfileImgRepository;
import com.example.roomlog.repository.user.UserRepository;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class ProfileImgService {

	@Autowired
	ProfileImgRepository profileImgRepository;
	@Autowired
	UserRepository userRepository;

	// application.properties(또는 application.yml)에 저장해둔 file.dir 프로퍼티 값을 가져와서 아래의 필드에 넣어줌
	@Value("${file.dir}")
	private String fileDir;
	
	// 프로필 사진 등록 또는 수정 전 형식변환하여 ProfileImg 객체에 담아 반환
	public ProfileImg updateProfileImg(User user, MultipartFile image) throws IOException {
		String originalImgName = image.getOriginalFilename();
		UUID uuid = UUID.randomUUID();
		String imgUuid = uuid.toString() + "_" + originalImgName;
		
		String setfileDir = "profile/" + getUploadDate();
		File uploadPath = new File(fileDir, setfileDir);
		
		// 경로가 존재하지 않는다면 필요한 모든 폴더 생성
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		File uploadImg = new File(uploadPath, imgUuid);
		
		// 매개변수로 받은 Multipart 객체에 담긴 이미지를 우리가 만든 경로와 이름으로 저장
		image.transferTo(uploadImg);
		
		// 썸네일 이미지 별도 저장
        Thumbnails.of(uploadImg)
        .size(350, 350)
        .toFile(new File(uploadPath, "th_" + imgUuid));

        long profileImgId = profileImgRepository.findByUser(user)
        		.map(ProfileImg::getProfileImgId)
        		.orElse(-1L);
        ProfileImg profileImg = null;
		
        if (profileImgId < 0) {
        	profileImg = ProfileImg.builder()
        		.user(user)
	        	.profileImgOriginal(originalImgName)
	        	.profileImgUuid(imgUuid)
	        	.profileImgPath("upload/" + setfileDir)
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
