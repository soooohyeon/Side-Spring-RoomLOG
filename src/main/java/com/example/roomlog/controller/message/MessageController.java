package com.example.roomlog.controller.message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/message")
public class MessageController {

	// 메세지함 메인 페이지 이동
	@GetMapping("")
	public String messageMainPage() {
		
		return "message/message";
	}
	
}
