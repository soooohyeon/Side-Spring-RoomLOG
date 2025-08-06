package com.example.roomlog.controller.mypage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.roomlog.dto.user.UserInfoDTO;
import com.example.roomlog.service.user.UserService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageContoller {
	
	private final UserService userService;

	@GetMapping("/mypage")
	public String maPageMain(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		UserInfoDTO user = userService.selectUser(userId);
		
		model.addAttribute("user", user);
		
		return "userPage/mypage";
	}
	
	@GetMapping("/profile/{userId}")
	public String userPage(@PathVariable long userId) {
		
		return "userPage/userPage";
	}
	
	
}
