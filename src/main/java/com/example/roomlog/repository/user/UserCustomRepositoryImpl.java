package com.example.roomlog.repository.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.QCommunity;
import com.example.roomlog.domain.follow.QFollow;
import com.example.roomlog.domain.user.QProfileImg;
import com.example.roomlog.domain.user.QSocialType;
import com.example.roomlog.domain.user.QUser;
import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	// 메인 - 팔로우 많은 순 상위 4명의 유저
	public List<UserDTO> selectFollowRankingList() {
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		QFollow f = QFollow.follow;
		QCommunity c = QCommunity.community;
		
		NumberExpression<Long> followerCount = f.fromUser.userId.countDistinct();
		NumberExpression<Long> communityCount = c.communityId.countDistinct();
		
		List<UserDTO> lists = jpaQueryFactory
			.select(Projections.fields(UserDTO.class,
				u.userId,
				u.userNickname,
				u.userIntro,
				pi.profileImgPath,
				pi.profileImgUuid,
		        ExpressionUtils.as(communityCount, "communityCount"),
		        ExpressionUtils.as(followerCount, "followerCount")
			))
			.from(u)
			.leftJoin(pi).on(pi.user.userId.eq(u.userId))
			.leftJoin(f).on(f.toUser.userId.eq(u.userId))
			.leftJoin(c).on(c.user.userId.eq(u.userId))
		    .groupBy(
				u.userId,
				u.userNickname,
				u.userIntro,
				pi.profileImgPath,
				pi.profileImgUuid
			)
			.orderBy(followerCount.desc())
			.offset(0)
		    .limit(4)
			.fetch();
		
		return lists;
	}
	
	// 마이페이지 (메인) - 유저 정보 출력
	public UserInfoDTO selectUser(long userId) {
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		QCommunity c = QCommunity.community;

		NumberExpression<Long> communityCount = c.communityId.countDistinct();
		
		UserInfoDTO user = jpaQueryFactory
			.select(Projections.fields(UserInfoDTO.class, 
				u.userId,
				u.userNickname,
				u.userIntro,
				u.isAgeVisible,
				u.userBirth,
				pi.profileImgPath,
				pi.profileImgUuid,
		        ExpressionUtils.as(communityCount, "communityCount")
			))
			.from(u)
			.leftJoin(pi).on(pi.user.userId.eq(u.userId))
			.leftJoin(c).on(c.user.userId.eq(u.userId))
			.where(u.userId.eq(userId))
			.fetchOne();
		
		return user;
	}
	
	// 마이페이지 (설정) - 수정할 유저 정보 출력
	public UserInfoDTO selectEditUser(long userId) {
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		
		UserInfoDTO user = jpaQueryFactory
			.select(Projections.fields(UserInfoDTO.class, 
				u.userId,
				ExpressionUtils.as(u.socialType.socialTypeId, "socialTypeId"),
				u.userEmail,
				u.userNickname,
				u.userIntro,
				u.userBirth,
				u.isAgeVisible,
				pi.profileImgPath,
				pi.profileImgUuid
			))
			.from(u)
			.leftJoin(pi).on(pi.user.userId.eq(u.userId))
			.where(u.userId.eq(userId))
			.fetchOne();
		
		return user;
	}
	
}
