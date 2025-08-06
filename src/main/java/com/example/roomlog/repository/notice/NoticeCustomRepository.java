package com.example.roomlog.repository.notice;

import java.util.List;

import com.example.roomlog.dto.notice.NoticeDTO;
import com.example.roomlog.dto.page.Criteria;

public interface NoticeCustomRepository {
	
	// 공지 게시글 목록
	List<NoticeDTO> selectListWithPaging(Criteria criteria);

	// 공지 게시글 상세 정보
	NoticeDTO selectViewOne(long noticeId);
	
}
