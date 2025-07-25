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
import com.example.roomlog.dto.page.Criteria;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
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
		
		BooleanBuilder builder = new BooleanBuilder();

		// 검색어 + 검색 조건 동적 조건 설정
		String keyword = criteria.getKeyword();
	    if (criteria.getCategory() != null && keyword != null) {
			switch(criteria.getCategory()) {
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
	    }
	    
	    // 정렬 동적 조건 설정
	    String sort = Optional.ofNullable(criteria.getSort()).orElse("newest");
	    OrderSpecifier<?> order = switch (sort) {
			case "newest"-> c.createDate.desc();
			case "comment" -> cm.commentId.count().desc();
			case "scrap" -> s.scrapId.count().desc();
			default -> c.createDate.desc();
	    };
			
		// 해시태그를 제외한 게시글 목록 담기
		List<CommunityListDTO> lists = jpaQueryFactory
			.select(Projections.fields(CommunityListDTO.class,
				c.communityId,
		        c.communityTitle,
		        c.communityContent,
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
		    .groupBy(c.communityId)
			.orderBy(order)
			.where(builder)
		    .offset((criteria.getPage() - 1) * criteria.getAmount())
		    .limit(criteria.getAmount())
			.fetch();
		
		return lists;
	}
	
}