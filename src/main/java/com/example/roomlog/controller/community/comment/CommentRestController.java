package com.example.roomlog.controller.community.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.dto.page.Page;
import com.example.roomlog.service.community.comment.CommentService;
import com.example.roomlog.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentRestController {
	
	private final CommentService commentService;

	// 댓글 목록
	@GetMapping("/comment-list/{communityId}")
	public Map<String, Object> selectParentList(@PathVariable("communityId") long communityId, Criteria criteria) {
	    Map<String, Object> result = new HashMap<>();
	    List<CommentDTO> parents = commentService.selectParentList(communityId, criteria);
	    int totalCount = commentService.countComment(communityId);
	    int partentCount = commentService.countParentComment(communityId);
	    Page page = new Page(criteria, partentCount);
	    
	    result.put("parents", parents);
	    result.put("totalCount", totalCount);
	    result.put("page", page);
	    
		return result;
	}
	
	// 대댓글 목록
	@GetMapping("/comment-list/child/{parentId}")
	public Map<String, Object> selectChildList(@PathVariable("parentId") long parentId, @PageableDefault(size = 5) Pageable pageable) {
		Map<String, Object> result = new HashMap<>();
		Slice<CommentDTO> childs = commentService.selectChildList(parentId, pageable);
		int childCount = commentService.countChildComment(parentId);
		
		result.put("childs", childs);
		result.put("totalCount", childCount);
		
		return result;
	}
	
	// 댓글, 대댓글 등록
	@PostMapping("/comment-regist")
	public ResponseEntity<?> insertComment(@RequestBody CommentDTO commentDTO, HttpSession session) {
		Long userId = SessionUtils.getUserId(session);
		if (userId == null) {
			throw new IllegalStateException("로그인이 필요한 서비스입니다.");
		}
		commentDTO.setUserId(userId);
		commentService.insertComment(commentDTO);
		
		String msg = 
				userId != null && userId > 0
				? commentDTO.getParentCommentId() == null
				? "댓글이 등록되었습니다."
						: "대댓글이 등록되었습니다."
							: "로그인 후 이용해 주세요.";
				return ResponseEntity.ok(Map.of("msg", msg));
	}
	
	// 댓글, 대댓글 수정
	@PatchMapping("/comment-edit/{commentId}")
	public void editComment(@PathVariable("commentId")long commentId, @RequestBody CommentDTO commentDTO, HttpSession session) {
		Long userId = SessionUtils.getUserId(session);
		if (userId == null) {
			throw new IllegalStateException("로그인이 필요한 서비스입니다.");
		}
		
		commentDTO.setCommentId(commentId);
		commentService.editComment(commentDTO);
	}
	
	// 댓글 대댓글 삭제
	@DeleteMapping("/comment-delete/{commentId}")
	public void  deleteComment(@PathVariable long commentId, HttpSession session) {
		Long userId = SessionUtils.getUserId(session);
		if (userId == null) {
			throw new IllegalStateException("로그인이 필요한 서비스입니다.");
		}
		
		commentService.deleteComment(commentId, "delete");
	}
	
}
