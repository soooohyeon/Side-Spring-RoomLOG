package com.example.roomlog.service.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;
import com.example.roomlog.repository.user.UserRepository;

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
//		
//		String fileName = "profile.png";
//		String contentType = "image/png";
//		byte[] content = "fake image data".getBytes(); // 실제 이미지는 필요 없음
//		MockMultipartFile mockImg = new MockMultipartFile("image", fileName, contentType, content);
//		// when
//		try {
//			userService.updateUserInfo(userDTO1, mockImg, "JOIN");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}		
//		// then
//		Optional<User> user = userRepository.findByUserId(userDTO1.getUserId());
//		assertThat(user).isNotEmpty();
		
		// given (마이페이지 수정)
		UserDTO userDTO2 = new UserDTO();
		userDTO2.setUserId(1);
		userDTO2.setUserNickname("test입니다");
		userDTO2.setIsAgeVisible(0);
		userDTO2.setUserIntro("한 줄 소개 테스트");
		MockMultipartFile mockImg2 = null;
		// when
		try {
			userService.updateUserInfo(userDTO2, mockImg2, "MYPAGE");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// then
		Optional<User> user = userRepository.findByUserId(userDTO2.getUserId());
		assertThat(user).isNotEmpty();
	}
	
	// 마이페이지(메인) - 유저 정보 출력
	@Test
	public void selectUserTest() {
		// given
		long userId = 1;
		// when
		UserInfoDTO user = userService.selectUser(userId);
		// then
		assertNotNull(user);
//		log.info("유저 : " + user);
	}
	
	// 마이페이지(메인) - 해당 유저가 작성한 커뮤니티 게시글 개수
	@Test
	public void countListTest() {
		// given
		long userId = 1;
		// when
		int count = userService.countList(userId);
		// then
		assertTrue(count > 0, "유저가 작성한 글이 없음");
//		log.info("작성한 게시글 개수 : " + count);
	}
	
	// 마이페이지(메인) - 해당 유저가 작성한 커뮤니티 게시글
	@Test
	public void selectListTest() {
		// given
		long userId = 1;
		Criteria criteria = new Criteria(null, null, null);
		// when
		List<CommunityListDTO> lists = userService.selectList(userId, criteria);
		// then
		assertNotNull(lists);
//		for(CommunityListDTO list : lists) {
//			log.info("작성한 게시글 : " + list);
//		}
	}
	
	// 마이페이지(메인) - 해당 유저가 스크랩한 커뮤니티 게시글 개수
	@Test
	public void countScrapListTest() {
		// given
		long userId = 1;
		// when
		int count = userService.countScrapList(userId);
		// then
		assertTrue(count > 0, "유저가 스크랩한 글이 없음");
//		log.info("스크랩한 게시글 개수 : " + count);
	}
	
	// 마이페이지(메인) - 해당 유저가 스크랩한 커뮤니티 게시글
	@Test
	public void selectScrapListTest() {
		// given
		long userId = 1;
		Criteria criteria = new Criteria(null, null, null);
		// when
		List<CommunityListDTO> lists = userService.selectScrapList(userId, criteria);
		// then
		assertNotNull(lists);
//		for(CommunityListDTO list : lists) {
//			log.info("스크랩한 게시글 : " + list);
//		}
	}
	
	// 마이페이지(설정) - 수정할 유저 정보 출력
	@Test
	public void selectEditUser() {
		// given
		long userId = 1;
		// when
		UserInfoDTO user = userService.selectEditUser(userId);
		// then
		assertNotNull(user);
//		log.info("수정할 유저 정보 : " + user);
	}
	
}
