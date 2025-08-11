package com.example.roomlog.controller.mypage;

import java.io.IOException;

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
	public String quitPage(HttpSession session) {
		System.out.println("컨트롤러 들어옴");
		
		return "userPage/mypage-quit";
	}
	
	// 유저 개인 페이지
	@GetMapping("/profile/{userId}")
	public String userPage(@PathVariable long userId) {
		
		return "userPage/userPage";
	}
	
	
}
