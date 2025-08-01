package com.example.roomlog.repository.community.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

	// 댓글 1개 조회 - 테스트 용
	Comment findByCommentId(long commentId);
	
	// 해당 게시글의 모든 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c " + 
			"	WHERE c.community.communityId = :communityId")
	int countByCommunityId(long communityId);

	// 해당 게시글의 부모 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c " + 
			"	WHERE c.community.communityId = :communityId " + 
			"	and c.parentComment is null")
	int countParentComment(long communityId);
	
	// 해당 게시글의 자식 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.parentComment.commentId = :parentId")
	int countChildComment(long parentId);
	
	// 댓글 삭제
	@Transactional
	void deleteByCommentId(long commentId);
	
}
