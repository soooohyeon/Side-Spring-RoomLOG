package com.example.roomlog.controller.mypage;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;
import com.example.roomlog.service.user.UserService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MypageContoller {
	
	private final UserService userService;

	// 마이페이지 (메인)
	@GetMapping("/mypage")
	public String myPageMain(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		UserInfoDTO user = userService.selectUser(userId);
		
		model.addAttribute("user", user);
		
		return "userPage/mypage";
	}
	
	// 마이페이지 (설정) - 개인 정보 수정
	@GetMapping("/mypage/settings")
	public String settingPage(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		UserInfoDTO user = userService.selectEditUser(userId);
		
		model.addAttribute("user", user);

		return "userPage/mypage-settings";
	}
	
	// 마이페이지 (설정) - 개인 정보 수정 완료
	@PostMapping("/mypage/settings/editOk")
	public String editUser(UserDTO userDTO, @RequestParam("one-image") MultipartFile image, HttpSession session) {
		long userId = SessionUtils.getUserId(session);
		userDTO.setUserId(userId);
		System.out.println(userDTO);
		
		try {
			userService.updateUserInfo(userDTO, image, "MYPAGE");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/mypage?editOk=true";
	}
	
	// 마이페이지 (탈퇴)
	@GetMapping("/mypage/quit")
	public String quitPage() {
		return "userPage/mypage-quit";
	}
	
	// 마이페이지 (탈퇴 처리)
	@GetMapping("/mypage/quitOk")
	public String quitUser(HttpSession session, HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		long userId = SessionUtils.getUserId(session);
		userService.deleteUser(userId);
		
		// 브라우저 쿠키 삭제
		new CookieClearingLogoutHandler("JSESSIONID", "remember-me", "access_token", "refresh_token");
		// 세션 무효화 + SecurityContext 제거
	    new SecurityContextLogoutHandler().logout(request, response, authentication);
	    
		return "redirect:/main?quitOk=true";
	}
	
	// 유저 개인 페이지
	@GetMapping("/profile/{userId}")
	public String userPage(@PathVariable long userId) {
		
		return "userPage/userPage";
	}
	
	
}
