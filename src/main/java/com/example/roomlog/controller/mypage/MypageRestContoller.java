package com.example.roomlog.controller.mypage;

import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.page.Page;
import com.example.roomlog.service.user.UserService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class MypageRestContoller {
	
	private final UserService userService;
	
	// 마이페이지 - 내가 작성한 게시글
	@GetMapping("/mypage/community/regist-list")
	public Map<String, Object> selectRegistList(HttpSession session, Criteria criteria) {
		long userId = SessionUtils.getUserId(session);
		
	    Map<String, Object> result = new HashMap<>();
		List<CommunityListDTO> lists = userService.selectList(userId, criteria);
	    int count = userService.countList(userId);
	    Page page = new Page(criteria, count);
	    
	    result.put("registList", lists);
	    result.put("registCount", count);
	    result.put("page", page);
		
		return result;
	}
	
	// 마이페이지 - 내가 스크랩한 게시글
	@GetMapping("/mypage/community/scrap-list")
	public Map<String, Object> selectScrapList(HttpSession session, Criteria criteria) {
		long userId = SessionUtils.getUserId(session);
		
		Map<String, Object> result = new HashMap<>();
		List<CommunityListDTO> lists = userService.selectScrapList(userId, criteria);
		int count = userService.countScrapList(userId);
		Page page = new Page(criteria, count);
		
		result.put("scrapList", lists);
		result.put("scrapCount", count);
		result.put("page", page);

		return result;
	}
	
}
