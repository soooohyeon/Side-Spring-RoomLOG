package com.example.roomlog.controller.community;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.community.CommunityDTO;
import com.example.roomlog.service.community.CommunityService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
	
	private final CommunityService communityService;
	
	//	게시판 - 목록
	@GetMapping("/community-list")
	public String communityListPage() {
		return "community/community";
	}

	//	게시판 - 글 작성
	@GetMapping("/community-regist")
	public String communityWritePage() {
		return "community/community-write";
	}
	
	// 게시판 - 작성 글 저장
	@PostMapping("/community-registOk")
	public String insertCommunity(CommunityDTO communityDTO, List<MultipartFile> images, HttpSession session) {
		long userNumber = (long) session.getAttribute("userNumber");
		communityDTO.setUserId(userNumber);
		
		try {
			communityService.insertCommunity(communityDTO, images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/community/community-list?registOk=true";
	}
	
}
