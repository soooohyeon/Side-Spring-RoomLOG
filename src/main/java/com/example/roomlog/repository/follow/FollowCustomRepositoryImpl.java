package com.example.roomlog.repository.follow;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.example.roomlog.domain.follow.QFollow;
import com.example.roomlog.domain.user.QProfileImg;
import com.example.roomlog.domain.user.QUser;
import com.example.roomlog.dto.follow.FollowDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowCustomRepositoryImpl implements FollowCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;
	
	// 내가 팔로우한 유저 목록
	public List<FollowDTO> selectFollowList(long userId, String keyword) {
		QFollow f = QFollow.follow;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		
		List<FollowDTO> lists = jpaQueryFactory
			.select(Projections.fields(FollowDTO.class, 
				f.followId,
				u.userId,
				u.userNickname,
				u.userIntro,
				pi.profileImgPath,
				pi.profileImgUuid
			))
			.from(f)
			.leftJoin(u).on(u.userId.eq(f.toUser.userId))
			.leftJoin(pi).on(pi.user.userId.eq(f.toUser.userId))
			.where(
				f.fromUser.userId.eq(userId),
				u.userNickname.containsIgnoreCase(keyword)
			)
			.orderBy(f.followId.desc())
			.fetch();
		
		return lists;
	}
	
	// 나를 팔로우한 팔로워 목록
	public List<FollowDTO> selectFollowerList(long userId, String keyword) {
		QFollow f = QFollow.follow;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		
		List<FollowDTO> lists = jpaQueryFactory
			.select(Projections.fields(FollowDTO.class,
				f.followId,
				u.userId,
				u.userNickname,
				u.userIntro,
				pi.profileImgPath,
				pi.profileImgUuid
			))
			.from(f)
			.leftJoin(u).on(u.userId.eq(f.fromUser.userId))
			.leftJoin(pi).on(pi.user.userId.eq(f.fromUser.userId))
			.where(
				f.toUser.userId.eq(userId),
				u.userNickname.containsIgnoreCase(keyword)
			)
			.orderBy(f.followId.desc())
			.fetch();
		
		return lists;
	}
	
}
