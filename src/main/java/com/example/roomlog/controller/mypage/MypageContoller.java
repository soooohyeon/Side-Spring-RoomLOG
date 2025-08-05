package com.example.roomlog.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageContoller {

	@GetMapping("/mypage")
	public String maPageMain(HttpSession session) {
		
		return "userPage/mypage";
	}
	
	@GetMapping("/profile/{userId}")
	public String userPage(@PathVariable long userId) {
		
		return "userPage/userPage";
	}
	
	
}
