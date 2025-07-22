package com.example.roomlog.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.service.user.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	
	// 회원가입 - 필수 정보 입력
	@Test
	public void insertUserTest() {
		// given
		String socialTypeName = "KAKAO";
		UserDTO user = new UserDTO();
		user.setUserEmail("dddd123@gmail.com");
		user.setUserNickname("dddd123@gmail.com");
		user.setUserBirth(LocalDate.parse("2000-01-01"));
		user.setIsAgeVisible(0);
		// when
		userService.insertUser(user, socialTypeName);
		// then
		List<User> list = userRepository.findAll();
		assertThat(list).isNotEmpty();
	}
	
	// 닉네임 중복 체크
	@Test
	void checkNicknameTest() {
		// given
		String nickname = "test";
		// when
		boolean exists = userRepository.existsByUserNickname(nickname);
		// then
		assertTrue(exists, "닉네임이 존재하지 않음");
	}
	
	// 닉네임, 한 줄 소개, 나이 공개 여부 수정 (회원가입시 선택정보 입력, 마이페이지 수정)
	@Test
	void updateUserInfoTest() {
//		// given (회원가입)
//		UserDTO userDTO1 = new UserDTO();
//		userDTO1.setUserId(1);
//		userDTO1.setUserIntro("한 줄 소개 테스트");
//		// when
//		userService.updateUserInfo(userDTO1, "JOIN");		
//		// then
//		Optional<User> user = userRepository.findByUserId(userDTO1.getUserId());
//		assertThat(user).isNotEmpty();
		
		// given (마이페이지 수정)
		UserDTO userDTO2 = new UserDTO();
		userDTO2.setUserId(1);
		userDTO2.setUserNickname("test입니다");
		userDTO2.setIsAgeVisible(0);
		userDTO2.setUserIntro("한 줄 소개 테스트");
		
		MultipartFile image = null;
		
		// when
		try {
			userService.updateUserInfo(userDTO2, image, "MYPAGE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		// then
		Optional<User> user = userRepository.findByUserId(userDTO2.getUserId());
		assertThat(user).isNotEmpty();
	}
}
