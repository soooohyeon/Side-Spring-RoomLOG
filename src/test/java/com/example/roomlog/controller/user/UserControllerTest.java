package com.example.roomlog.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.controller.user.LoginController;
import com.example.roomlog.controller.user.UserRestController;
import com.example.roomlog.dto.user.UserDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
public class UserControllerTest {

	@Autowired
	UserRestController userRestController;
	@Autowired
	LoginController loginController;
	
	// 회원가입 - 필수 정보 입력
	@Test
	public void requiredJoinTest() {
		// given
		UserDTO userDTO = new UserDTO();
		userDTO.setUserNickname("aaa");
		userDTO.setUserBirth(LocalDate.parse("2000-01-01T00:00:00"));
		userDTO.setIsAgeVisible(0);
		
	    MockHttpSession session = new MockHttpSession();
	    session.setAttribute("oauthSocialType", "KAKAO");
	    session.setAttribute("oauthEmail", "aaaa123@gmail.com");
		
	    // when
	    String result = loginController.insertUser(session, userDTO);
		
		// then
	    assertEquals("login/join-optional", result);
	}
	
	// 닉네임 중복 체크
	@Test
	void checkNicknameTest() {
		// given
		String nickname = "test";
		// when
		ResponseEntity<Boolean> exists = userRestController.checkNickname(nickname);
		// then
		assertTrue(exists.getBody(), "닉네임이 존재하지 않음");
	}
	
}
