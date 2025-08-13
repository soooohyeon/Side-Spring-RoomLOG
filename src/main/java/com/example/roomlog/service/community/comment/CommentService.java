package com.example.roomlog.service.community.comment;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.roomlog.domain.community.comment.Comment;
import com.example.roomlog.domain.community.comment.Comment.CommentBuilder;
import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.community.CommunityRepository;
import com.example.roomlog.repository.community.comment.CommentRepository;
import com.example.roomlog.repository.user.UserRepository;
import com.example.roomlog.util.AgeUtils;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	private final CommunityRepository communityRepository;
	
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
				.user(userRepository.findByUserId(commentDTO.getUserId()).get())
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
	public void deleteComment(long commentId, String temp) {
		Comment comment = commentRepository.findByCommentId(commentId);
		int commentCount = commentRepository.countChildComment(commentId);
		int childCount = 0; // 자식 댓글 개수
		
		if (commentCount == 0 && comment.getParentComment() != null) {
			// 자식 댓글의 삭제를 눌렀을 경우
			childCount = commentRepository.countChildComment(comment.getParentComment().getCommentId());
			int deleteStatus = comment.getParentComment().getIsDeleted();
			
			if (childCount == 1 && (deleteStatus == 1 || deleteStatus == 2)) {
				// 현재 자식 댓글이 자신 1개이고 부모 댓글이 삭제 상태인 경우 부모 댓글도 삭제
				commentRepository.deleteByCommentId(comment.getParentComment().getCommentId());
			} else {
				// 자식 댓글이 1개보다 많을 경우 해당 자식 댓글만 삭제
				commentRepository.deleteByCommentId(commentId);
			}
		} else if (commentCount == 0) {
			// 부모 댓글에 자식 댓글이 없을 때 삭제를 누른 경우
			commentRepository.deleteByCommentId(commentId);
		} else if (commentCount > 0 && temp.equals("delete")) {
			// 게시글 - 부모 댓글에 자식 댓글이 있을 때 삭제를 누른 경우
			commentRepository.updateDeleteStatus(comment.getCommentId());
		} else if (commentCount > 0 && temp.equals("quit")) {
			// 회원 탈퇴 - 부모 댓글에 자식 댓글이 있는 경우
			commentRepository.updateQuitStatus(comment.getCommentId());
		}
	}
	
}
