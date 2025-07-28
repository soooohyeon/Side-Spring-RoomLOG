package com.example.roomlog.repository.community;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.QComment;
import com.example.roomlog.domain.community.QCommunity;
import com.example.roomlog.domain.community.QScrap;
import com.example.roomlog.domain.user.QProfileImg;
import com.example.roomlog.domain.user.QUser;
import com.example.roomlog.dto.community.CommunityListDTO;
import com.example.roomlog.dto.community.CommunityViewDTO;
import com.example.roomlog.dto.page.Criteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommunityCustomRepositoryImpl implements CommunityCustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	// 커뮤니티 게시글 목록
	public List<CommunityListDTO> selectListWithPaging(Criteria criteria) {
		QCommunity c = QCommunity.community;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		QComment cm = QComment.comment;
		QScrap s = QScrap.scrap;
		
		// 검색어 + 검색 조건 동적 조건 설정
		BooleanBuilder builder = new BooleanBuilder();
		String keyword = criteria.getKeyword();
		String category = criteria.getCategory();
	    if (category != null && keyword != null) {
	    	builder = setSearch(category, keyword, c);
	    }
	    
	    // 정렬 동적 조건 설정
	    String sort = Optional.ofNullable(criteria.getSort()).orElse("newest");
			
		// 해시태그를 제외한 게시글 목록 담기
		List<CommunityListDTO> lists = jpaQueryFactory
			.select(Projections.fields(CommunityListDTO.class,
				c.communityId,
		        c.communityTitle,
		        c.communityContent,
		        c.communityId.countDistinct().as("communityCount"),
				c.createDate,
				u.userId,
				u.userNickname,
				u.userBirth,
				u.isAgeVisible,
				pi.profileImgPath,
				pi.profileImgUuid,
		        cm.commentId.countDistinct().as("commentCount"),
		        s.scrapId.countDistinct().as("scrapCount")
				)
			)
			.from(c)
			.join(c.user, u)
			.leftJoin(pi).on(pi.user.eq(u))
		    .leftJoin(cm).on(cm.community.eq(c))
		    .leftJoin(s).on(s.community.eq(c))
		    .groupBy(
				c.communityId,
				c.communityTitle,
				c.communityContent,
				c.createDate,
				u.userId,
				u.userNickname,
				u.userBirth,
				u.isAgeVisible,
				pi.profileImgPath,
				pi.profileImgUuid)
			.orderBy(setSort(sort, c, cm, s))
			.where(builder)
		    .offset((criteria.getPage() - 1) * criteria.getAmount())
		    .limit(criteria.getAmount())
			.fetch();
		
		return lists;
	}

	// 현재 리스트의 게시글 개수 - 검색 전, 후 결과
	public long countSearchResult (Criteria criteria) {
		QCommunity c = QCommunity.community;
		
		// 검색어 + 검색 조건 동적 조건 설정
		BooleanBuilder builder = new BooleanBuilder();
		String keyword = criteria.getKeyword();
		String category = criteria.getCategory();
	    if (category != null && keyword != null) {
	    	builder = setSearch(category, keyword, c);
	    }
	    
	    Long count = jpaQueryFactory
	    	.select(c.count())
	    	.from(c)
	    	.where(builder)
	    	.fetchOne();
	    
	    return count == null ? 0 : count;
	}
	
	// 검색어 + 검색 조건 동적 조건 설정
	public BooleanBuilder setSearch(String category, String keyword, QCommunity c) {
		BooleanBuilder builder = new BooleanBuilder();
		
		switch(category) {
		case "all" :
			builder.and(c.communityTitle.containsIgnoreCase(keyword)
				.or(c.communityContent.containsIgnoreCase(keyword))
				.or(c.user.userNickname.containsIgnoreCase(keyword))
				);
			break;
		case "nickname" :
			builder.and(c.user.userNickname.containsIgnoreCase(keyword));
			break;
		case "title-content" :
			builder.and(c.communityTitle.containsIgnoreCase(keyword)
					.or(c.communityContent.containsIgnoreCase(keyword))
					);
			break;
		case "title" :
			builder.and(c.communityTitle.containsIgnoreCase(keyword));
			break;
		case "content" :
			builder.and(c.communityContent.containsIgnoreCase(keyword));
			break;
		}
		
		return builder;
	}
	
	// 정렬 동적 조건 설정
	public OrderSpecifier<?> setSort(String sort, QCommunity c, QComment cm, QScrap s) {
		OrderSpecifier<?> order = switch (sort) {
			case "newest"-> c.communityId.desc();
			case "comment" -> Expressions.numberPath(Long.class, "commentCount").desc();
			case "scrap" -> Expressions.numberPath(Long.class, "scrapCount").desc();
			default -> c.communityId.desc();
		};
		
		return order;
	}
	
	// 커뮤니티 게시글 상세 정보
	public CommunityViewDTO selectViewOne(long communityId) {
		QCommunity c = QCommunity.community;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		QScrap s = QScrap.scrap;
		
		CommunityViewDTO post = jpaQueryFactory
				.select(Projections.fields(CommunityViewDTO.class,
					c.communityId,
					c.communityTitle,
					c.communityContent,
					c.createDate,
					c.modifiedDate,
					u.userId,
					u.userNickname,
					u.isAgeVisible,
					u.userBirth,
					u.userIntro,
					pi.profileImgPath,
					pi.profileImgUuid,
			        s.scrapId.countDistinct().as("scrapCount")
				))
				.from(c)
				.join(c.user, u)
				.leftJoin(pi).on(pi.user.eq(u))
				.leftJoin(s).on(s.community.eq(c))
				.where(c.communityId.eq(communityId))
				.fetchOne();
		
		return post;
	}
	
}