package com.example.roomlog.service.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.dto.community.CommunityDTO;
import com.example.roomlog.service.community.CommunityService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityServiceTest {

	@Autowired
	CommunityService communityService;
	
	// 커뮤니티 글 등록
	@Test
	public void insertCommunityTest() {
		CommunityDTO communityDTO = new CommunityDTO();
		communityDTO.setUserId(1);
		communityDTO.setCommunityTitle("제목 Test");
		communityDTO.setCommunityContent("내용 Test");
		long communityId = communityService.insertCommunity(communityDTO);
		
		assertNull(communityId);
		assertNotNull(communityId);
	}
	
}
