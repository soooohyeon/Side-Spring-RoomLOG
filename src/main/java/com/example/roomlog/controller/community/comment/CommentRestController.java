package com.example.roomlog.controller.community.comment;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.service.community.comment.CommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
	
	private final CommentService commentService;
	
	@PostMapping("/comment-registOk")
	public ResponseEntity<?> insertComment(@RequestBody CommentDTO commentDTO, HttpSession session) {
		System.out.println();
		System.out.println(commentDTO);
		Long userNumber = (long) session.getAttribute("userNumber");
		if (userNumber != null && userNumber > 0) {
			commentDTO.setUserId(userNumber);
			commentService.insertComment(commentDTO);
		}
		
	    String msg = commentDTO.getParentCommentId() == null
	        ? "댓글이 등록되었습니다."
	        : "대댓글이 등록되었습니다.";
		
		return ResponseEntity.ok(Map.of("msg", msg));
	}
	
}
