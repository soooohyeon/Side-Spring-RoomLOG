package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

	// 프로필 사진 조회
	Optional<ProfileImg> findByUser(User user);

	Optional<ProfileImg> findByProfileImgId(long profileImgId);
	
}
