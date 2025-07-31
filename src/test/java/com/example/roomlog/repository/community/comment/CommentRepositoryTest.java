package com.example.roomlog.repository.community.comment;

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

import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	// 해당 게시글의 모든 댓글 개수 조회
	@Test
	public void countByCommunityIdTest() {
		// given
		long communityId = 48;
		// when
		int count = commentRepository.countByCommunityId(communityId);
		// then
		assertEquals(count, 4);
	}
	
	// 해당 게시글의 부모 댓글 개수 조회
	@Test
	public void countParentCommentTest() {
		// given
		long communityId = 48;
		// when
		int count = commentRepository.countParentComment(communityId);
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
		int count = commentRepository.countChildComment(parentId);
		// then
		log.info("count : " + count);
		assertEquals(count, 9);
	}

	// 부모 댓글 목록 조회
	@Test
	public void selectParentCommentsWithPagingTest() {
		// given
		long communityId = 48;
		Criteria criteria = new Criteria();
		criteria.setPage(2);
		// when
		List<CommentDTO> lists = commentRepository.selectParentCommentsWithPaging(communityId, criteria);
		// then
		assertNotNull(lists);
//		for (CommentDTO comment : lists) {
//			log.info("댓글 : " + comment);
//		}
	}
	
	// 자식 댓글 목록 조회
	@Test
	public void selectChildCommentsTest() {
		// given
		long parentId = 48;
		Pageable pageable = PageRequest.of(0, 5); 
		// when
		Slice<CommentDTO> lists = commentRepository.selectChildComments(parentId, pageable);
		// then
	    assertThat(lists).isNotNull();
	    assertThat(lists.getContent());
	}
	
}
