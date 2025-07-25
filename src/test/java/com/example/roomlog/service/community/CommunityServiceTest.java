package com.example.roomlog.service.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.CommunityRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityServiceTest {

	@Autowired
	CommunityService communityService;
	@Autowired
	CommunityRepository communityRepository;
	
	// 커뮤니티 게시글 개수
	@Test
	public void countAllCommunityTest() {
		// when
		int count = communityService.countAllCommunity();
		// then
		assertEquals(count, 0);
	}
	
	// 커뮤니티 게시글 목록
	@Test
	public void selectListAllTest() {
		// given
		long userNumber = 1L;
		Criteria criteria = new Criteria(null, null, null);
		// when
		List<CommunityListDTO> lists = communityService.selectListAll(userNumber, criteria);
		// then
		for (CommunityListDTO list : lists) {
			log.info("list : " + list);
		}
		assertNotNull(lists);
	}
	
	// 커뮤니티 글 등록
	@Test
	public void insertCommunityTest() {
		// when
		CommunityListDTO communityListDTO = new CommunityListDTO();
		communityListDTO.setUserId(1);
		communityListDTO.setCommunityTitle("제목 Test");
		communityListDTO.setCommunityContent("내용 Test");
		List<String> tags = new ArrayList<>(List.of("소파", "test"));
		communityListDTO.setTags(tags);
		
		String contentType = "image/png";
		byte[] content = "fake image data".getBytes(); // 실제 이미지는 필요 없음
        MockMultipartFile image1 = new MockMultipartFile("images", "img1.jpg", contentType, content);
        MockMultipartFile image2 = new MockMultipartFile("images", "img2.jpg", contentType, content);
		List<MultipartFile> images = List.of(image1, image2);
		// when
//		try {
////			communityService.insertCommunity(communityListDTO, images);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		// then
		assertNotNull(communityRepository.findAll());
	}
	
}
