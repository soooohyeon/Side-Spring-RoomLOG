package com.example.roomlog.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.repository.user.ProfileImgRepository;

@Service
public class ProfileImgService {

	@Autowired
	ProfileImgRepository profileImgRepository;

//	// 프로필 사진 등록 및 수정 (회원가입시 선택정보 입력, 마이페이지 수정)
//	public void updateUserInfo(MultipartFile image) {
//		
//	}
	
}
