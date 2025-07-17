package com.example.roomlog.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.user.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {
	
	private final UserService userService;

	// 닉네임 중복 검사
	@GetMapping("/check-nickname")
	public ResponseEntity<Boolean> checkNickname(String nickname) {
		boolean exists = userService.checkNickname(nickname);
		
		return ResponseEntity.ok(exists);
	}
	
}
