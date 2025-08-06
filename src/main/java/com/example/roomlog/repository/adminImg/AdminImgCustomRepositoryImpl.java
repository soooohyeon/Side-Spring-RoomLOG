package com.example.roomlog.repository.adminImg;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.adminImg.QAdminImg;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AdminImgCustomRepositoryImpl implements AdminImgCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	// 공지 상세 - 해당 공지 게시글의 모든 이미지 경로 조회
	public List<String> selectNoticeImgList(long noticeId) {
		QAdminImg ai = QAdminImg.adminImg;
		
		List<Tuple> lists = jpaQueryFactory
			.select(
				ai.adminImgPath,
				ai.adminImgUuid
			)
			.from(ai)
			.where(ai.notice.noticeId.eq(noticeId))
			.fetch();
		
		// 경로 + 이미지명을 넣은 리스트로 변환
		List<String> imgUrls = lists.stream()
			.map(tuple -> tuple.get(ai.adminImgPath) + "/" + tuple.get(ai.adminImgUuid))
			.collect(Collectors.toList());
		
		return imgUrls;
	}
	
}
