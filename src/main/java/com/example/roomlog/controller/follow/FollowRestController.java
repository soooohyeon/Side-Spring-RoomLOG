package com.example.roomlog.controller.follow;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.service.follow.FollowService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowRestController {

	private final FollowService followService;
	
	// 팔로우
	@PostMapping("/follow-save/{toUserId}")
	public void insertFollow(@PathVariable long toUserId, HttpSession session) {
		long fromUserId = SessionUtils.getUserId(session);
		followService.insertFollow(fromUserId, toUserId);
	}
	
	// 팔로우 취소
	@DeleteMapping("/follow-cancel/{toUserId}")
	public void deleteFollow(@PathVariable long toUserId, HttpSession session) {
		long fromUserId = SessionUtils.getUserId(session);
		followService.cancelFollow(fromUserId, toUserId);
	}
	
}