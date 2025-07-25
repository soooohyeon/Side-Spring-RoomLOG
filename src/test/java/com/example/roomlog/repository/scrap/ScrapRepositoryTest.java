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

import com.example.roomlog.domain.community.ScrapId;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class ScrapRepositoryTest {

	@Autowired
	ScrapRepository scrapRepository;
	
	// 각 게시글의 스크랩 수와 스크랩 여부
	@Test
	public void countScrapAndIsScrappedTest() {
		// given
		List<Long> communityIds = List.of(9L, 17L, 20L, 21L, 22L, 23L, 24L);
		long userNumber = 1;
		// when
		Map<Long, Boolean> lists = scrapRepository.checkIsScrapped(communityIds, userNumber);
		// then
		assertNotNull(lists);
		assertEquals(lists.size(), 2);
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
