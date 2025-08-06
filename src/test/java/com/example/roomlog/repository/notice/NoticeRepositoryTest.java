package com.example.roomlog.repository.notice;

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
public class NoticeRepositoryTest {

	@Autowired
	NoticeRepository noticeRepository;
	
	// 공지 게시글 목록
	@Test
	public void selectListWithPagingTest() {
		// given
		Criteria criteria = new Criteria();
		// when
		List<NoticeDTO> lists = noticeRepository.selectListWithPaging(criteria);
		// then
		assertNotNull(lists);
//		for (NoticeDTO list : lists) {
//			log.info("공지 게시글 : " + list);
//		}
	}
	
}
