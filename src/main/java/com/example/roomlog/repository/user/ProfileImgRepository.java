package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

	// 프로필 이미지 조회
	Optional<ProfileImg> findByUser(User user);
	// 수정할 프로필 이미지 조회
	Optional<ProfileImg> findByProfileImgId(long profileImgId);
	
}
