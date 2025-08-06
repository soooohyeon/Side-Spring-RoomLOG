package com.example.roomlog.repository.adminImg;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class AdminImgRepositoryTest {

	@Autowired
	AdminImgRepository adminImgRepository;
	
	// 공지 상세 - 해당 공지 게시글의 모든 이미지 경로 조회
	public void selectNoticeImgListTest() {
		// given
		long noticeId = 1;
		// when
		List<String> lists = adminImgRepository.selectNoticeImgList(noticeId);
		// then
		assertNotNull(lists);
	}
	
}
