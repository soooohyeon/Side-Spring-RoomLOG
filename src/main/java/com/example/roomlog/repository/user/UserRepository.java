package com.example.roomlog.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.user.User;


public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {
	
//	OAuth - 이메일이 존재하는지 체크
	Optional<User> findByUserEmail(String userEmail);

}
