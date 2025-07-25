package com.example.roomlog.controller.scrap;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.scrap.ScrapService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scrap")
public class ScrapRestController {

	private final ScrapService scrapService;
	
	// 스크랩 하기
	@PostMapping("/scrap-save/{communityId}")
	public void insertScrap(@PathVariable long communityId, HttpSession session) {
		long userNumber = (long) session.getAttribute("userNumber");
		scrapService.insertScrap(communityId, userNumber);
	}
	
	// 스크랩 취소
	@DeleteMapping("/scrap-cancel/{communityId}")
	public void cancelScrap(@PathVariable long communityId, HttpSession session) {
		long userNumber = (long) session.getAttribute("userNumber");
		scrapService.deleteScrap(communityId, userNumber);
	}
	
}
