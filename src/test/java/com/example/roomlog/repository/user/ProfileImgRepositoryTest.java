package com.example.roomlog.repository.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.user.ProfileImg;
import com.example.roomlog.domain.user.User;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class ProfileImgRepositoryTest {

	@Autowired
	ProfileImgRepository profileImgRepository;
	@Autowired
	UserRepository userRepository;

	// 프로필 사진 조회
	@Test
	public void findByUserTest() {
		// given
		long userId = 1;
		User user = userRepository.findByUserId(userId).get();
		// when
		long profileImgId = profileImgRepository.findByUserId(userId)
        		.map(ProfileImg::getProfileImgId)
        		.orElse(-1L);
		// then
//		log.info("프로필 사진 ID : " + profileImgId);
		assertNotNull(user);
		assertEquals(-1L, profileImgId);
	}
	
}
