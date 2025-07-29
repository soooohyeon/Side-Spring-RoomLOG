package com.example.roomlog.repository.community.comment;

import java.util.List;

import com.example.roomlog.dto.community.comment.CommentDTO;
import com.example.roomlog.dto.page.Criteria;

public interface CommentCustomRepository {
	
	// 부모 댓글 목록 조회
	List<CommentDTO> selectParentCommentsWithPaging(long communityId, Criteria criteria);

	// 자식 댓글 목록 조회
	List<CommentDTO> selectChildCommentsWithPaging(long communityId, List<Long> parentIds);
	
}
