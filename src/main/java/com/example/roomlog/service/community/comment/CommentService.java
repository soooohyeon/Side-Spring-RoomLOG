package com.example.roomlog.service.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.repository.community.comment.CommentRepository;
import com.example.roomlog.repository.user.UserRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commetRepository;
	@Autowired
	UserRepository userRepository;
	
	public void insertComment(String content, long userNumber) {
		
		Comment comment = Comment.builder()
				.commentContent(content)
				.writerUser(userRepository.findByUserId(userNumber).get())
				.build();
		
	}
	
}
