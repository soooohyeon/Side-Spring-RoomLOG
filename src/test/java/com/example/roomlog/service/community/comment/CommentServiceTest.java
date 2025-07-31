package com.example.roomlog.service.community.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.comment.CommentRepository;

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
	
	// 해당 게시글의 모든 댓글 개수 조회
	@Test
	public void countCommentTest() {
		// given
		long communityId = 48;
		// when
		int count = commentService.countComment(communityId);
		// then
		assertEquals(count, 4);
	}
	
	// 해당 게시글의 부모 댓글 개수 조회
	@Test
	public void countParentCommentTest() {
		// given
		long communityId = 48;
		// when
		int count = commentService.countParentComment(communityId);
		// then
		log.info("count : " + count);
		assertEquals(count, 17);
	}
	
	// 해당 게시글의 자식 댓글 개수 조회
	@Test
	public void countChildCommentTest() {
		// given
		long parentId = 36;
		// when
		int count = commentService.countChildComment(parentId);
		// then
		log.info("count : " + count);
		assertEquals(count, 9);
	}
	
	// 부모 댓글 목록 조회
	@Test
	public void selectParentListTest() {
		// given
		long communityId = 48;
		Criteria criteria = new Criteria();
		// when
		List<CommentDTO> lists = commentService.selectParentList(communityId, criteria);
		// then
		assertThat(lists).hasSize(4);
		assertThat(lists.get(0).getChildComment()).hasSize(3);
	}
	
	// 자식 댓글 목록 조회
	@Test
	public void selectChildListTest() {
		// given
		long parentId = 15;
		Pageable pageable = PageRequest.of(0, 5); 
		// when
		Slice<CommentDTO> lists = commentService.selectChildList(parentId, pageable);
		// then
	    assertThat(lists).isNotNull();
	    assertThat(lists.getContent());
	    
	    for (CommentDTO list : lists) {
	    	log.info("자식 댓글 : " + list);
	    }
	}
}
