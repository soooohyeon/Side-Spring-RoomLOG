package com.example.roomlog.service.notice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.roomlog.dto.notice.NoticeDTO;
import com.example.roomlog.dto.page.Criteria;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class NoticeServiceTest {

	@Autowired
	NoticeService noticeService;
	
	// 공지 게시글 총 개수
	@Test
	public void countAllNoticeTest() {
		// given
		Criteria criteria = new Criteria();
		// when
		int count = noticeService.countAllNotice();
		// then
		assertEquals(count, 2);
//		log.info("공지 개수 : " + count);
	}
	
	// 공지 게시글 목록
	@Test
	public void selectListWithPagingTest() {
		// given
		Criteria criteria = new Criteria();
		// when
		List<NoticeDTO> lists = noticeService.selectListAll(criteria);
		// then
		assertNotNull(lists);
//		for (NoticeDTO list : lists) {
//			log.info("공지 게시글 : " + list);
//		}
	}
	
	// 공지 상세 게시글 정보
	@Test
	public void selectViewOneTest() {
		// given
		long noticeId = 1;
		// when
		NoticeDTO post = noticeService.selectViewOne(noticeId);
		// then
		assertNotNull(post);
	}
	
}
