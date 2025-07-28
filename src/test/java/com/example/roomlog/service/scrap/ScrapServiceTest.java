package com.example.roomlog.service.scrap;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.roomlog.domain.scrap.ScrapId;
import com.example.roomlog.repository.scrap.ScrapRepository;
import com.example.roomlog.service.scrap.ScrapService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class ScrapServiceTest {

	@Autowired
	ScrapService scrapService;
	@Autowired
	ScrapRepository scrapRepository;
	
	// 스크랩 하기
	@Test
	public void insertScrapTest() {
		// given
		long communityId = 31;
		long userNumber = 1;
		// when
		scrapService.insertScrap(communityId, userNumber);
		// then
		assertNull(scrapRepository.findById(new ScrapId(userNumber, communityId)));
	}
	
	// 스크랩 취소
	@Test
	public void deleteScrapTest() {
		// when
		scrapService.deleteScrap(1L, 32L);
		// then
		assertNull(scrapRepository.findById(new ScrapId(1L, 32L)));
	}
	
}
