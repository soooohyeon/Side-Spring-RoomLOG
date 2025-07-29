package com.example.roomlog.repository.community.comment;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.comment.QComment;
import com.example.roomlog.domain.user.QProfileImg;
import com.example.roomlog.domain.user.QUser;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {
	private final JPAQueryFactory jpaQueryFactory;
	
	// 부모 댓글 목록 조회
	public List<CommentDTO> selectParentCommentsWithPaging(long communityId, Criteria criteria) {
		QComment c = QComment.comment;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		
		List<CommentDTO> parents = jpaQueryFactory
			.select(Projections.fields(CommentDTO.class, 
				c.commentId,
				c.community.communityId,
				u.userId,
				u.userNickname,
				u.isAgeVisible,
				u.userBirth,
				pi.profileImgPath,
				pi.profileImgUuid,
				c.parentComment.commentId.as("parentCommentId"),
				c.commentContent,
				c.isDeleted,
				c.createDate,
				c.modifiedDate
			))
			.from(c)
			.join(c.writerUser, u)
			.leftJoin(pi).on(pi.user.eq(u))
			.orderBy(c.commentId.desc())
			.where(c.community.communityId.eq(communityId),
				c.parentComment.commentId.isNull())
			.offset((criteria.getPage() - 1) * criteria.getAmount())
			.limit(criteria.getAmount())
			.fetch();
		
		return parents;
	};
	
	// 자식 댓글 목록 조회
	public Slice<CommentDTO> selectChildComments(long parentId, Pageable pageable) {
		QComment c = QComment.comment;
		QUser u = QUser.user;
		QProfileImg pi = QProfileImg.profileImg;
		
		List<CommentDTO> childs = jpaQueryFactory
			.select(Projections.fields(CommentDTO.class, 
				c.commentId,
				c.community.communityId,
				u.userId,
				u.userNickname,
				u.isAgeVisible,
				u.userBirth,
				pi.profileImgPath,
				pi.profileImgUuid,
				c.parentComment.commentId.as("parentCommentId"),
				c.commentContent,
				c.isDeleted,
				c.createDate,
				c.modifiedDate
			))
			.from(c)
			.join(c.writerUser, u)
			.leftJoin(pi).on(pi.user.eq(u))
			.orderBy(c.commentId.desc())
			.where(c.parentComment.commentId.eq(parentId))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize() + 1)
			.fetch();

	    boolean hasNext = childs.size() > pageable.getPageSize();
	    if (hasNext) {
	    	// 마지막 제거해서 다음 페이지 기준
	    	childs.remove(childs.size() - 1);
	    }
	    
		return new SliceImpl<>(childs, pageable, hasNext);
	};
	
}
