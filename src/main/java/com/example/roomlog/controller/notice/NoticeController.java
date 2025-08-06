package com.example.roomlog.controller.notice;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.roomlog.dto.notice.NoticeDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.page.Page;
import com.example.roomlog.service.notice.NoticeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

	private final NoticeService noticeService;
	
	// 공지 - 목록
	@GetMapping("/notice-list")
	public String noticeListPage(Criteria criteria, Model model) {
		int countNotice = noticeService.countAllNotice();
		List<NoticeDTO> lists = noticeService.selectListAll(criteria);
		Page page = new Page(criteria, countNotice);
		
		model.addAttribute("countNotice", countNotice);
		model.addAttribute("lists", lists);
		model.addAttribute("page", page);
		
		return "/notice/notice";
	}
	
	// 공지 - 상세 보기
	@GetMapping("/notice-view")
	public String noticeViewPage(@RequestParam long noticeId, Model model) {
		NoticeDTO post = noticeService.selectViewOne(noticeId);
		model.addAttribute("post", post);
		
		return "/notice/notice-view";
	}
	
	
}
