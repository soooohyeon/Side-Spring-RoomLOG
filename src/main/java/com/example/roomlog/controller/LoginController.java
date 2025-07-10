package com.example.roomlog.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	// 로그인 페이지
	@GetMapping("")
	public String loginPage() {
		return "login/login";
	}
	
	// 회원가입일 경우 - 회원 필수 정보 입력받는 페이지
	@GetMapping("/join-required")
	public String requiredJoinPage() {
		return "login/join-required";
	}
	
	// 회원가입일 경우 - 회원 선택 정보(프로필 사진, 한 줄 소개) 입력받는 페이지
	@GetMapping("/join-optional")
	public String optionalJoinPage() {
		return "login/join-optional";
	}
	
}
