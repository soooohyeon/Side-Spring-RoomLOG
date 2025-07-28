package com.example.roomlog.repository.community.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.comment.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	
	
}
