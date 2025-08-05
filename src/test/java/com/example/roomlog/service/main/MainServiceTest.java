package com.example.roomlog.service.main;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.dto.community.CommunityListDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class MainServiceTest {

	@Autowired
	MainService mainService;
	
	// 커뮤니티 게시글 스크랩 순 상위 3개
	@Test
	public void selectListAllTest() {
		// given
		long userNumber = 1L;
		// when
		List<CommunityListDTO> lists = mainService.selectScrapRankList(userNumber);
		// then
		for (CommunityListDTO list : lists) {
			log.info("list : " + list);
		}
		assertNotNull(lists);
	}
}
