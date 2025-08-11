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

	// 마이페이지 (메인)
	@GetMapping("/mypage")
	public String maPageMain(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		UserInfoDTO user = userService.selectUser(userId);
		
		model.addAttribute("user", user);
		
		return "userPage/mypage";
	}
	
	// 마이페이지 (설정)
	@GetMapping("/mypage/settings")
	public String settingPage(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		UserInfoDTO user = userService.selectEditUser(userId);
		
		model.addAttribute("user", user);

		return "userPage/mypage-settings";
	}
	
	// 마이페이지 (탈퇴)
	@GetMapping("/mypage/quit")
	public String quitPage(HttpSession session) {
		System.out.println("z컨트롤러 들어옴");
		
		return "userPage/mypage-quit";
	}
	
	// 유저 개인 페이지
	@GetMapping("/profile/{userId}")
	public String userPage(@PathVariable long userId) {
		
		return "userPage/userPage";
	}
	
	
}
