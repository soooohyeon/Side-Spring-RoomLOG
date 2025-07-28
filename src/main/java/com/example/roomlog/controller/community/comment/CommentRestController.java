package com.example.roomlog.controller.community.comment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.community.comment.CommentService;
import com.example.roomlog.service.follow.FollowService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
	
	private final CommentService commentService;
	
	@PostMapping("/comment-regist")
	public void insertComment() {
		
	}
	
}
