package com.example.roomlog.repository.community;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
	
	// 메인 - 커뮤니티 스크랩 순 게시글 상위 3개
	@Test
	public void selectScrapRankingListTest() {
		// when
		List<CommunityListDTO> lists = communityRepository.selectScrapRankingList();
		// then
		assertNotNull(lists);
//		for (CommunityListDTO list : lists) {
//			log.info("list : " + list);
//		}
//		log.info("list count : " + lists.size());
	}
	
	// 마이페이지 - 해당 유저가 작성한 커뮤니티 게시글 개수
	@Test
	public void countListByUserTest() {
		// given
		long userId = 1;
		// when
		int count = communityRepository.countListByUser(userId);
		// then
		assertTrue(count > 0, "유저가 작성한 글이 없음");
//		log.info("작성한 게시글 개수 : " + count);
	}
		
	// 마이페이지 - 해당 유저가 작성한 커뮤니티 게시글
	@Test
	public void selectListByUserTest() {
		// given 
		long userId = 1;
		Criteria criteria = new Criteria(null, null, null);
		// when
		List<CommunityListDTO> lists = communityRepository.selectListByUser(userId, criteria);
		// then
		assertNotNull(lists);
//		for (CommunityListDTO list : lists) {
//			log.info("list : " + list);
//		}
	}
	
	// 마이페이지 - 해당 유저가 스크랩한 커뮤니티 게시글
	@Test
	public void selectScrapListByUserTest() {
		// given 
		long userId = 1;
		Criteria criteria = new Criteria(null, null, null);
		// when
		List<CommunityListDTO> lists = communityRepository.selectScrapListByUser(userId, criteria);
		// then
		assertNotNull(lists);
//		for (CommunityListDTO list : lists) {
//			log.info("list : " + list);
//		}
	}
	
	// 커뮤니티 게시글 목록 (유저 + 프로필사진 + 게시글)
	@Test
	public void selectListAllTest() {
		Criteria criteria = new Criteria(null, null, "comment");
		// when
		List<CommunityListDTO> lists = communityRepository.selectListWithPaging(criteria);
		// then
		assertNotNull(lists);
//		for (CommunityListDTO list : lists) {
//			log.info("list : " + list);
//		}
//		log.info("list count : " + lists.size());
	}
	
	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	@Test
	public void countSearchCountTest() {
		Criteria criteria = new Criteria(null, null, "comment");
		// when
		long count = communityRepository.countSearchResult(criteria);
		// then
		assertNotNull(count);
//		log.info("list count : " + count);
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
	
	// 게시글 삭제
	@Test
	public void deleteByCommunityIdTest() {
		// given
		long communityId = 1;
		// when
		communityRepository.deleteByCommunityId(communityId);
		// then
		assertNull(communityRepository.findByCommunityId(communityId));
	}
	
}
