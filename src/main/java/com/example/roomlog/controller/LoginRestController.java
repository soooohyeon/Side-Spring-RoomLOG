package com.example.roomlog.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.domain.user.User;

@RestController
@RequestMapping("/login")
public class LoginRestController {

	// 회원 가입 - 필수 정보 저장
	@PostMapping("/join-required-data")
	public String requiredJoin(@RequestParam User user) {
		
		
		return "redirect:/login/join-optional";
	}
	
}
