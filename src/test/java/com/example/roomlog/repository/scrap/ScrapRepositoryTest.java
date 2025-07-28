package com.example.roomlog.repository.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.scrap.ScrapId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class ScrapRepositoryTest {

	@Autowired
	ScrapRepository scrapRepository;
	
	// 커뮤니티 게시글 목록 - 해당 게시글의 스크랩 여부
	@Test
	public void checkIsScrappedListTest() {
		// given
		List<Long> communityIds = List.of(9L, 17L, 20L, 21L, 22L, 23L, 24L);
		long userNumber = 1;
		// when
		Map<Long, Boolean> lists = scrapRepository.checkIsScrappedList(communityIds, userNumber);
		// then
		assertNotNull(lists);
		assertEquals(lists.size(), 2);
	}
	
	// 커뮤니티 게시글 상세 - 해당 게시글의 스크랩 여부
	@Test
	public void checkIsScrappedTest() {
		// given
		long communityId = 31;
		long userNumber = 1;
		// when
		boolean isScrapped = scrapRepository.checkIsScrapped(userNumber, communityId);
		// then
		log.info("스크랩 여부 : " + isScrapped);
		assertNotNull(isScrapped);
	}
	
	// 스크랩 취소
	@Test
	public void deleteByScrapIdTest() {
		// given
		ScrapId scrapId = new ScrapId(1L, 32L);
		// when
		scrapRepository.deleteByScrapId(scrapId);
		// then
		assertNull(scrapRepository.findById(scrapId));
	}
	
}
