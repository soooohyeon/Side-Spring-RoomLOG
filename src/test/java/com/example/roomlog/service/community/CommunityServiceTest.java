package com.example.roomlog.service.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.dto.community.CommunityDTO;
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
	
	// 커뮤니티 글 등록
	@Test
	public void insertCommunityTest() {
		// when
		CommunityDTO communityDTO = new CommunityDTO();
		communityDTO.setUserId(1);
		communityDTO.setCommunityTitle("제목 Test");
		communityDTO.setCommunityContent("내용 Test");
		List<String> tags = new ArrayList<>(List.of("소파", "test"));
		communityDTO.setTags(tags);
		// when
		communityService.insertCommunity(communityDTO);
		// then
		assertNotNull(communityRepository.findAll());
	}
	
}
