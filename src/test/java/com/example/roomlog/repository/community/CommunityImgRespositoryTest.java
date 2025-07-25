package com.example.roomlog.repository.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.CommunityImg;
import com.example.roomlog.repository.community.image.CommunityImgRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityImgRespositoryTest {

	@Autowired
	CommunityRepository communityRepository;
	@Autowired
	CommunityImgRepository communityImgRepository;
	
	// 해당 게시글의 대표 이미지 조회
	@Test
	public void findMainThumbnailTest() {
		// given
		List<Long> communityIds = List.of(20L, 21L, 22L, 23L);
		// when
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		// then
		for (CommunityImg image : images) {
			log.info("대표 이미지 : " + image);
		}
		assertNotNull(images);
	}
	
}
