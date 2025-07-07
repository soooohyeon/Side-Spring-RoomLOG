package com.example.roomlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	
//	로그인 페이지
	@GetMapping("")
	public String loginPage() {
		return "login/login";
	}
	
}
