package com.example.roomlog.repository.community;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.QCommunity;
import com.example.roomlog.dto.community.CommunityDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommunityCustomRepositoryImpl implements CommunityCustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	// 커뮤니티 게시글 목록
	public List<CommunityDTO> selectListAll() {
		QCommunity community = QCommunity.community;
//		QCommunityImg
		
		
		return null;
	}
	
}
