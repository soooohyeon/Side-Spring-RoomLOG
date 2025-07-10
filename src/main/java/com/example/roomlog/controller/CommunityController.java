package com.example.roomlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/community")
public class CommunityController {
	
	//	게시판 - 목록
	@GetMapping("/community-list")
	public String communityListPage() {
		return "community/community";
	}
	
}
