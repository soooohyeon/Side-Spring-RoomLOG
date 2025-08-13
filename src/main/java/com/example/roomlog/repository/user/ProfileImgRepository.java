package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.roomlog.domain.user.ProfileImg;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

	// 프로필 이미지 조회
	@Query ("SELECT pi FROM ProfileImg pi where pi.user.userId = :userId")
	Optional<ProfileImg> findByUserId(long userId);
	
	// 수정할 프로필 이미지 조회
	Optional<ProfileImg> findByProfileImgId(long profileImgId);

}
