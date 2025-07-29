package com.example.roomlog.service.community.comment;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.domain.community.comment.Comment.CommentBuilder;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
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
	
	// 해당 게시글의 댓글 개수 조회
	public int countComment(long communityId) {
		return commentRepository.countByCommunityId(communityId);
	}
	
	// 댓글 목록 조회
	public List<CommentDTO> selectListAll(long communityId, Criteria criteria) {
		// 부모 댓글만 조회
		List<CommentDTO> parents = commentRepository.selectParentCommentsWithPaging(communityId, criteria);
		// 부모 댓글 번호만 빼오기
		List<Long> parentIds = parents.stream()
			.map(CommentDTO::getCommentId)
			.collect(Collectors.toList());
		
		List<CommentDTO> childs = commentRepository.selectChildCommentsWithPaging(communityId, parentIds);
		
		return null;
	}
	
}
