package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.user.SocialType;
import com.example.roomlog.domain.user.User;


public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
	
	// OAuth - 이메일이 존재하는지 체크
	Optional<User> findByUserEmailAndSocialType(String userEmail, SocialType socialType);
	
	// 해당 유저의 ID로 정보 조회
	Optional<User> findByUserId(long userId);
	
	// 닉네임 중복 체크
	boolean existsByUserNickname(String userNickname);
	
}
