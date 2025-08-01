package com.example.roomlog.service.community.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.domain.community.comment.Comment.CommentBuilder;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.comment.CommentRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.util.AgeUtils;

@Service
public class CommentService {

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CommunityRepository communityRepository;
	
	// 해당 게시글의 모든 댓글 개수 조회
	public int countComment(long communityId) {
		return commentRepository.countByCommunityId(communityId);
	}
	
	// 해당 게시글의 부모 댓글 개수 조회
	public int countParentComment(long communityId) {
		return commentRepository.countParentComment(communityId);
	}
	
	// 해당 게시글의 자식 댓글 개수 조회
	public int countChildComment(long parentId) {
		return commentRepository.countChildComment(parentId);
	}
	
	// 부모 댓글 목록 조회
	public List<CommentDTO> selectParentList(long communityId, Criteria criteria) {
		List<CommentDTO> parents = commentRepository.selectParentCommentsWithPaging(communityId, criteria);
		for (CommentDTO parent : parents) {
			parent.setUserAge(AgeUtils.getAgeLabel(parent.getIsAgeVisible(), parent.getUserBirth()));
		}
		
		return parents;
	}
	
	// 자식 댓글 목록 조회
	public Slice<CommentDTO> selectChildList(long parentId, Pageable pageable) {
		Slice<CommentDTO> childs = commentRepository.selectChildComments(parentId, pageable);
		for (CommentDTO child : childs) {
			child.setUserAge(AgeUtils.getAgeLabel(child.getIsAgeVisible(), child.getUserBirth()));
		}
		
		return childs;
	}

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
	
	// 댓글 수정
	public void editComment(CommentDTO commentDTO) {
		Comment comment = commentRepository.findByCommentId(commentDTO.getCommentId());
		comment.updateComment(commentDTO.getCommentContent());
		commentRepository.save(comment);
	}
	
	// 댓글 삭제
	public void deleteComment(long commentId) {
		Comment comment = commentRepository.findByCommentId(commentId);
		int childCount = commentRepository.countChildComment(commentId);
		System.out.println("자식 개수 : " + childCount);
		
		if (childCount == 0) {
			System.out.println("대댓글이 없는 경우");
			// 대댓글이 없을 경우 삭제
			commentRepository.deleteByCommentId(commentId);
		} else {
			System.out.println("대댓글 있음");
			// 대댓글이 있을 경우 삭제 상태로 변경
			comment.deleteParentComment();
			commentRepository.save(comment);
		}
		
	}
	
}
