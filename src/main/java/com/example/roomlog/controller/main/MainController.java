package com.example.roomlog.controller.main;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.service.main.MainService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;
	
//	메인 화면
	@GetMapping({"/", "/main"})
	public String home(HttpSession session, Model model) {
		long userId = SessionUtils.getUserId(session);
		List<UserDTO> followRanklist = mainService.selectFollowRankList(userId);
		List<CommunityListDTO> scrapRanklist = mainService.selectScrapRankList(userId);
		
		model.addAttribute("followRanklist", followRanklist);
		model.addAttribute("scrapRanklist", scrapRanklist);
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("게시글 개수 : " + scrapRanklist.size());
		for (CommunityListDTO post : scrapRanklist) {
			System.out.println("게시글 : " + post);
		}
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++");
		
	    return "main";
	}
}
