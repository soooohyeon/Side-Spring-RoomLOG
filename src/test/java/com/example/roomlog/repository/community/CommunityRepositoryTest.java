package com.example.roomlog.repository.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.Community;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.page.Criteria;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@Slf4j
public class CommunityRepositoryTest {

	@Autowired
	CommunityRepository communityRepository;
	
	// 커뮤니티 게시글 목록 (유저 + 프로필사진 + 게시글)
	@Test
	public void selectListAllTest() {
		Criteria criteria = new Criteria(null, null, "comment");
		// when
		List<CommunityListDTO> lists = communityRepository.selectListWithPaging(criteria);
		// then
		for (CommunityListDTO list : lists) {
			log.info("list : " + list);
		}
		log.info("list count : " + lists.size());
		assertNotNull(lists);
	}
	
	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	@Test
	public void countSearchCountTest() {
		Criteria criteria = new Criteria(null, null, "comment");
		// when
		long count = communityRepository.countSearchResult(criteria);
		// then
		log.info("list count : " + count);
		assertNotNull(count);
	}
	
	// 게시글 1개 조회
	@Test
	public void findByCommunityIdTest() {
		// given
		long communityId = 9;
		// when
		Community commu = communityRepository.findByCommunityId(communityId);
		// then
		assertNotNull(commu);
	}
	
	// 커뮤니티 게시글 상세 정보
	@Test
	public void selectViewOneTest() {
		// given
		long communityId = 32;
		// when
		CommunityViewDTO post = communityRepository.selectViewOne(communityId);
		// then
		assertNotNull(post);
	}
	
}
