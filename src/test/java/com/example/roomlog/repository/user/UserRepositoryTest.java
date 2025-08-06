package com.example.roomlog.repository.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	SocialTypeRepository socialTypeRepository;
	
	// 메인 - 팔로우 많은 순 상위 4명의 유저
	@Test
	public void selectFollowRankingListTest() {
		// when
		List<UserDTO> lists = userRepository.selectFollowRankingList();
		// then
		assertNotNull(lists);
//		for (UserDTO list : lists) {
//			log.info("유저 랭킹 : " + list);
//		}
	}
	
	// 회원가입 - 유저 필수 정보 저장
	@Test
	public void insertUserRequiredTest() {
		// given
		SocialType socialType = socialTypeRepository.findBySocialTypeName("KAKAO");
		User user = User.builder()
				.socialType(socialType)
				.userEmail("ddd123@gmail.com")
				.userNickname("ddd")
				.userBirth(LocalDate.parse("2000-01-01"))
				.isAgeVisible(0)
				.build();
		// when
		userRepository.save(user);
		// then
		List<User> userList = userRepository.findAll();
		assertThat(userList).hasSize(1);
	}
	
	// 닉네임 중복 체크
	@Test
	public void checkNicknameTest() {
		// given
		String nickname = "test";
		// when
		boolean exists = userRepository.existsByUserNickname(nickname);
		// then
		assertTrue(exists, "닉네임이 존재하지 않음");
	}
	
	// 해당 유저의 ID로 정보 조회
	@Test
	public void updateUserTest() {
		// given
		long userNumber = 1L;
		// when
		Optional<User> userDTO = userRepository.findByUserId(userNumber);
		// then
		assertNotNull(userDTO);		
	}
	
	// 마이페이지 (메인) - 유저 정보 출력
	@Test
	public void selectUserTest() {
		// given
		long userNumber = 1L;
		// when
		UserInfoDTO userInfoDTO = userRepository.selectUser(userNumber);
		// then
		assertNotNull(userInfoDTO);		
		log.info("유저 1명 정보 : " + userInfoDTO);
	}

	
}
