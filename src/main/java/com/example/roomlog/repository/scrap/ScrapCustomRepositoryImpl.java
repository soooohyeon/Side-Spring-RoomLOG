package com.example.roomlog.repository.scrap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.QCommunity;
import com.example.roomlog.domain.community.QScrap;
import com.example.roomlog.domain.user.QUser;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	// 커뮤니티 게시글 목록 - 해당 게시글의 스크랩 여부
	public Map<Long, Boolean> checkIsScrappedList(List<Long> communityIds, Long userNumber) {
		QScrap s = QScrap.scrap;

		List<Tuple> result = jpaQueryFactory
			.select(
				s.community.communityId,
				s.user.userId )
			.from(s)
			.where(
				s.community.communityId.in(communityIds),
				s.user.userId.eq(userNumber))
			.fetch();
		
		return result.stream()
			.collect(Collectors.toMap(
				t -> t.get(s.community.communityId),
				t -> true
			));
	}
	
	// 커뮤니티 게시글 상세 - 해당 게시글의 스크랩 여부
	public Boolean checkIsScrapped(Long userNumber, Long communityId) {
		QScrap s = QScrap.scrap;
		
		Long count = jpaQueryFactory
			.select(s.user.userId.count())
			.from(s)
			.where(
				s.community.communityId.eq(communityId),
				s.user.userId.eq(userNumber))
			.fetchOne();
		
		return count != null && count > 0;
	}
	
}
