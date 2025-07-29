package com.example.roomlog.repository.community.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentCustomRepository {

	// 댓글 1개 조회
	Comment findByCommentId(long commentId);
	
	// 해당 게시글의 댓글 개수 조회
	@Query("SELECT COUNT(c) FROM Comment c " + 
			"	WHERE c.community.communityId = :communityId")
	int countByCommunityId(long communityId);
	
}
