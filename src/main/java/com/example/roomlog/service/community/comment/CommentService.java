package com.example.roomlog.service.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.domain.community.comment.Comment.CommentBuilder;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.comment.CommentRepository;
import com.example.roomlog.repository.user.UserRepository;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommunityRepository communityRepository;

	// 댓글 등록
	public void insertComment(CommentDTO commentDTO) {
		CommentBuilder builder = Comment.builder()
				.writerUser(userRepository.findByUserId(commentDTO.getUserId()).get())
				.community(communityRepository.findByCommunityId(commentDTO.getCommunityId()))
				.commentContent(commentDTO.getCommentContent());
		
		if (commentDTO.getParentCommentId() != null) {
		    builder.parentComment(commentRepository.findByCommentId(commentDTO.getParentCommentId()));
		}
		
		Comment comment = builder.build();
		commentRepository.save(comment);
	}
	
}
