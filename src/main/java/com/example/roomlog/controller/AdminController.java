package com.example.roomlog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

//	관리자 - 메인 화면
	@GetMapping({"/main"})
	public String mainPage() {
	    return "admin/admin-main";
	}
	
}
