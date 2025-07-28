package com.example.roomlog.service.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.repository.community.comment.CommentRepository;
import com.example.roomlog.service.community.comment.CommentService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommentServiceTest {

	@Autowired
	CommentService commentService;
	@Autowired
	CommentRepository commentRepository;
	
	// 댓글 등록
	@Test
	public void insertComment() {
		// given
		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setUserId(1L);
		commentDTO.setCommunityId(48L);
		commentDTO.setCommentContent("TEST");
		// when
		commentService.insertComment(commentDTO);
		// then
		Comment comment = commentRepository.findByCommentId(commentDTO.getCommunityId());
		assertNotNull(comment);
	}
	
}
