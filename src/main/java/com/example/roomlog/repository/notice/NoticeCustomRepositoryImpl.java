package com.example.roomlog.repository.notice;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.notice.QNotice;
import com.example.roomlog.dto.notice.NoticeDTO;
import com.example.roomlog.dto.page.Criteria;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class NoticeCustomRepositoryImpl implements NoticeCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	// 공지 게시글 목록
	public List<NoticeDTO> selectListWithPaging(Criteria criteria) {
		QNotice n = QNotice.notice;
		
		List<NoticeDTO> lists = jpaQueryFactory
			.select(Projections.fields(NoticeDTO.class,
				n.noticeId,
				n.noticeTitle,
				n.noticeContent,
				n.createDate
			))
			.from(n)
			.orderBy(n.noticeId.desc())
			.offset((criteria.getPage() - 1) * criteria.getAmount())
			.limit(criteria.getAmount())
			.fetch();
		
		return lists;
	}

	// 공지 게시글 상세 정보
	public NoticeDTO selectViewOne(long noticeId) {
		QNotice n = QNotice.notice;

		NoticeDTO post = jpaQueryFactory
			.select(Projections.fields(NoticeDTO.class,
				n.noticeId,
				n.noticeTitle,
				n.noticeContent,
				n.createDate
			))
			.from(n)
			.where(n.noticeId.eq(noticeId))
			.fetchOne();
		
		return post;
	}
	
}
