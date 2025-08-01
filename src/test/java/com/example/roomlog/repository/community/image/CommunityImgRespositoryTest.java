package com.example.roomlog.repository.community.image;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.image.CommunityImg;
import com.example.roomlog.dto.community.image.CommunityImgDTO;
import com.example.roomlog.repository.community.CommunityRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityImgRespositoryTest {

	@Autowired
	CommunityRepository communityRepository;
	@Autowired
	CommunityImgRepository communityImgRepository;
	
	// 커뮤니티 게시글 목록 - 해당 게시글의 대표 이미지 조회
	@Test
	public void findMainThumbnailTest() {
		// given
		List<Long> communityIds = List.of(20L, 21L, 22L, 23L);
		// when
		List<CommunityImg> images = communityImgRepository.findFirstImagesByCommunityIds(communityIds);
		// then
//		for (CommunityImg image : images) {
//			log.info("대표 이미지 : " + image);
//		}
		assertNotNull(images);
	}

	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 이미지 경로 조회
	@Test
	public void selectImgListTest() {
		// given
		long communityId = 48;
		// when
		List<String> imgUrls = communityImgRepository.selectImgList(communityId);
		// then
//		for (String url : imgUrls) {
//			log.info("이미지 경로 조회 : " + url);
//		}
		assertNotNull(imgUrls);
	}
	
	// 해당 게시글의 이미지 조회
	@Test
	public void findbyCommunityImgTest() {
		// given
		long communityId = 13;
		// when
		List<CommunityImgDTO> images = communityImgRepository.findCommunityImg(communityId);
		// then
//		for (CommunityImgDTO img : images) {
//			log.info("이미지 조회 : " + img);
//		}
		assertNotNull(images);
	}
	
	// 해당 게시글의 이미지 1개씩 조회
	@Test
	public void findCommunityImgByImageIdTest() {
		// given
		long imageId = 1;
		// when
		CommunityImgDTO image = communityImgRepository.findCommunityImgByImageId(imageId);
		// then
		log.info("이미지 조회 : " + image);
		assertNotNull(image);
	}

	// 해당 게시글의 해시태그 삭제
	@Test
	public void deleteByCommunityIdTest() {
		// given
		long communityId = 20;
		// when
		communityImgRepository.deleteByCommunityId(communityId);
		// then
		List<CommunityImgDTO> images = communityImgRepository.findCommunityImg(communityId);
		assertNotNull(images);
	}
	
}
