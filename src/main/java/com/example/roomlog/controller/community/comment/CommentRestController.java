package com.example.roomlog.controller.community.comment;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.service.community.comment.CommentService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
	
	private final CommentService commentService;
	
	@PostMapping("/comment-registOk")
	public ResponseEntity<?> insertComment(@RequestBody CommentDTO commentDTO, HttpSession session) {
		Long userId = SessionUtils.getUserId(session);
		if (userId == null) {
            throw new IllegalStateException("로그인이 필요한 서비스입니다.");
        }
		
		commentDTO.setUserId(userId);
		commentService.insertComment(commentDTO);
		
	    String msg = commentDTO.getParentCommentId() == null
	        ? "댓글이 등록되었습니다."
	        : "대댓글이 등록되었습니다.";
		
		return ResponseEntity.ok(Map.of("msg", msg));
	}
	
	public void selectListAll(long communityId, Criteria criteria) {
		
	}
	
}
