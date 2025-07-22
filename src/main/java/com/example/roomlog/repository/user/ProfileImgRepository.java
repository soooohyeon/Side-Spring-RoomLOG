package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.user.ProfileImg;

public interface ProfileImgRepository extends JpaRepository<ProfileImg, Long> {

	Optional<ProfileImg> findByProfileImgId(long profileImgId);
	
}
