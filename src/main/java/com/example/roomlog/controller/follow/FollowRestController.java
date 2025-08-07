package com.example.roomlog.controller.follow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.dto.follow.FollowDTO;
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
	
	// 해당 유저의 팔로워 목록
	@GetMapping("/follower-list/{targetUserId}")
	public Map<String, Object> selectFollowerList(@PathVariable long targetUserId, String keyword) {
		Map<String, Object> result = new HashMap<>();
		List<FollowDTO> lists = followService.selectFollowerList(targetUserId, keyword);
		int followerCount = followService.countFollower(targetUserId);
		
		result.put("lists", lists);
		result.put("count", followerCount);
		
		return result;
	}
	
	// 해당 유저가 팔로우한 유저 목록
	@GetMapping("/follow-list/{targetUserId}")
	public Map<String, Object> selectFollowList(@PathVariable long targetUserId, String keyword) {
		Map<String, Object> result = new HashMap<>();
		List<FollowDTO> lists = followService.selectFollowList(targetUserId, keyword);
		int followCount = followService.countFollow(targetUserId);
		
		result.put("lists", lists);
		result.put("count", followCount);

		return result;
	}
}