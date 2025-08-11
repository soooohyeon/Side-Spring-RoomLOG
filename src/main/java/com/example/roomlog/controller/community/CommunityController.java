package com.example.roomlog.controller.community;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.community.CommunityEditDTO;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityRegistDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.page.Page;
import com.example.roomlog.service.community.CommunityService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/community")
@RequiredArgsConstructor
public class CommunityController {
	
	private final CommunityService communityService;
	
	// 게시판 - 목록
	@GetMapping("/community-list")
	public String communityListPage(Criteria criteria, Model model, HttpSession session) {
		long userId = SessionUtils.getUserId(session);
		
		int countCommunity = communityService.countAllCommunity();
		int countSearchResult = communityService.countSearchResult(criteria);
		List<CommunityListDTO> lists = communityService.selectListAll(userId, criteria);
        Page page = new Page(criteria, countSearchResult);
        
		model.addAttribute("countCommunity", countCommunity);
		model.addAttribute("countSearchResult", countSearchResult);
		model.addAttribute("lists", lists);
		model.addAttribute("page", page);
		
		return "community/community";
	}
	
	// 게시판 - 상세 보기
	@GetMapping("/community-view")
	public String communityViewPage(@RequestParam long communityId, HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);

		CommunityViewDTO post = communityService.selectViewOne(userId, communityId);
		model.addAttribute("post", post);
		
		return "community/community-view";
	}

	// 게시판 - 글 작성
	@GetMapping("/community-regist")
	public String communityWritePage() {
		return "community/community-write";
	}
	
	// 게시판 - 작성 글 저장
	@PostMapping("/community-registOk")
	public String insertCommunity(CommunityRegistDTO communityRegistDTO, List<MultipartFile> images, HttpSession session) {
		long userId = SessionUtils.getUserId(session);
		communityRegistDTO.setUserId(userId);
		
		try {
			communityService.insertCommunity(communityRegistDTO, images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/community/community-list?registOk=true";
	}

	// 게시판 - 글 수정
	@GetMapping("/community-edit")
	public String editCommunity(@RequestParam long communityId, Model model) {
		CommunityEditDTO post = communityService.selectViewOneBeforeEdit(communityId);
		model.addAttribute("post", post);
		
		return "community/community-edit";
	}
	
	// 게시판 - 글 수정 처리
	@PostMapping("/community-editOk/{communityId}")
	public String editCommunity(@PathVariable long communityId, CommunityEditDTO communityEditDTO, List<MultipartFile> images) {
		communityEditDTO.setCommunityId(communityId);
		
		try {
			communityService.editCommunity(communityEditDTO, images);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "redirect:/community/community-list?editOk=true";
	}
	
	// 게시판 - 글 삭제 처리
	@GetMapping("/community-deleteOk")
	public String deleteCommunity(long communityId) {
		communityService.deleteCommunity(communityId);
		
		return "redirect:/community/community-list?deleteOk=true";
	}
	
}
