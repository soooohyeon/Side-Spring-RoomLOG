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
import com.example.roomlog.repository.community.comment.CommentRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommentRepositoryTest {

	@Autowired
	CommentRepository commentRepository;
	
	// 해당 게시글의 댓글 개수 조회
	@Test
	public void countByCommunityIdTest() {
		// given
		long communityId = 48;
		// when
		int count = commentRepository.countByCommunityId(communityId);
		// then
		assertEquals(count, 4);
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
