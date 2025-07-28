package com.example.roomlog.repository.community.hashtag;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.QCommunityHashtag;
import com.example.roomlog.domain.community.QHashtag;
import com.example.roomlog.dto.community.HashtagDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HashtagCustomRepositoryImpl implements HashtagCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	// 커뮤니티 게시글 목록 - 각 게시글의 해시태그
	public Map<Long, List<String>> selectAllHashtagList(List<Long> communityIds) {
		QCommunityHashtag ch = QCommunityHashtag.communityHashtag;
		QHashtag h = QHashtag.hashtag;
		
		List <HashtagDTO> tags = jpaQueryFactory
			.select(Projections.constructor(HashtagDTO.class,
				ch.community.communityId,
				h.hashtagName
			))
			.from(ch)
			.join(ch.hashtag, h)
			.where(ch.community.communityId.in(communityIds))
			.fetch();
	
		Map<Long, List<String>> lists = tags.stream()
			.collect(Collectors.groupingBy(
				HashtagDTO::getCommunityId,
				Collectors.mapping(HashtagDTO::getHashtagName, Collectors.toList())
			));
		
		return lists;
	}
	
	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 해시태그 조회
	public List<String> selectHashtagList (long communityId) {
		QCommunityHashtag ch = QCommunityHashtag.communityHashtag;
		QHashtag h = QHashtag.hashtag;
		
		List<String> lists = jpaQueryFactory
			.select(h.hashtagName)
			.from(ch)
			.join(h).on(ch.hashtag.hashtagId.eq(h.hashtagId))
			.where(ch.community.communityId.eq(communityId))
			.orderBy(h.hashtagId.asc())
			.fetch();
		
		return lists;
	}
	
}
