package com.example.roomlog.controller.user;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.service.user.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	
	private final UserService userService;
	
	// 로그인 페이지
	@GetMapping("")
	public String loginPage() {
		return "login/login";
	}

	// 회원가입 - 회원 필수 정보 입력
	@GetMapping("/join-required")
	public String requiredJoinPage() {
		return "login/join-required";
	}

	// 회원 가입 - 필수 정보 저장
	@PostMapping("/join-required-data")
	public String insertUser(HttpSession session, UserDTO userDTO) {
		String socialTypeName = (String) session.getAttribute("oauthSocialType");
		String email = (String) session.getAttribute("oauthEmail");
		
		userDTO.setUserEmail(email);
		int userNumber = userService.insertUser(userDTO, socialTypeName);
		
	    session.setAttribute("userNumber", userNumber);
	    session.setAttribute("nickname", userDTO.getUserNickname());
		session.removeAttribute("oauthEmail");
		session.removeAttribute("oauthSocialType");
		
		return "redirect:/login/join-optional";
	}
	
	// 회원가입 - 선택 정보(프로필 사진, 한 줄 소개) 입력
	@GetMapping("/join-optional")
	public String optionalJoinPage(HttpSession session, Model model) {
		String nickname = (String) session.getAttribute("nickname");
        model.addAttribute("nickname", nickname);
        
		return "login/join-optional";
	}

	// 회원 가입 - 선택 정보 저장
	@PostMapping("/join-optional-data")
	public String updateUserInfo(@RequestParam("one-image") MultipartFile image, HttpSession session, UserDTO userDTO) {
		int userNumber = (Integer) session.getAttribute("userNumber");
		userDTO.setUserId(userNumber);
		
		System.out.println();
		System.out.println(userDTO);
		
		try {
			userService.updateUserInfo(userDTO, image, "JOIN");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		session.removeAttribute("nickname");
		
		return "redirect:/main";
	}
}
