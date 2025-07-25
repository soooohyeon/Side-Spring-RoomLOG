package com.example.roomlog.repository.scrap;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import com.example.roomlog.domain.community.QScrap;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ScrapCustomRepositoryImpl implements ScrapCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	// 해당 게시글의 스크랩 여부
	public Map<Long, Boolean> checkIsScrapped(List<Long> communityIds, Long userNumber) {
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
	
	
}
