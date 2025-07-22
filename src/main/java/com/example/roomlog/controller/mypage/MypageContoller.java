package com.example.roomlog.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MypageContoller {

	@GetMapping("")
	public String mapageMainPage(HttpSession session) {
		
		return "userPage/mypage";
	}
	
	
}
