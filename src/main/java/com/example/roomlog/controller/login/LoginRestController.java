package com.example.roomlog.controller.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginRestController {
	
	private final UserService userService;

	// 닉네임 중복 검사
	@GetMapping("/user/check-nickname")
	public ResponseEntity<Boolean> checkNickname(String nickname) {
		boolean exists = userService.checkNickname(nickname);
		
		return ResponseEntity.ok(exists);
	}
	
}
