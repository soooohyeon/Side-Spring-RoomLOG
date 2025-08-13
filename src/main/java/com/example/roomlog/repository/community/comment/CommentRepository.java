package com.example.roomlog.repository.community.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

	// 댓글 1개 조회 - 테스트 용
	Comment findByCommentId (long commentId);
	
	// 해당 게시글의 모든 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c " + 
			"	WHERE c.community.communityId = :communityId")
	int countByCommunityId (long communityId);

	// 해당 게시글의 부모 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c " + 
			"	WHERE c.community.communityId = :communityId " + 
			"	and c.parentComment is null")
	int countParentComment (long communityId);
	
	// 해당 게시글의 자식 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c WHERE c.parentComment.commentId = :parentId")
	int countChildComment (long parentId);
	
	// 자식 댓글 존재하는 부모 댓글은 삭제 상태로 변경
	@Modifying
	@Query("UPDATE Comment c SET c.isDeleted = 1 WHERE c.commentId = :commentId")
	void updateDeleteStatus(long commentId);

	// 회원 탈퇴시 자식 댓글 존재하는 부모 댓글은 삭제 상태로 변경
	@Modifying
	@Query("UPDATE Comment c SET c.isDeleted = 2, c.user = null " + 
			"	WHERE c.commentId = :commentId")
	void updateQuitStatus(long commentId);
	
	// 댓글 삭제
	@Transactional
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	void deleteByCommentId(long commentId);
	
	// 회원 탈퇴시 해당 회원이 작성한 댓글 번호만 조회
	@Query("SELECT c.commentId FROM Comment c WHERE c.user.userId = :userId")
	List<Long> findAllCommentByUserId (long userId);
	
}
