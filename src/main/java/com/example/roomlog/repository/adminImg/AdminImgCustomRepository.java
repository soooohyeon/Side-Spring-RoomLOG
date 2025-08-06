package com.example.roomlog.repository.adminImg;

import java.util.List;

public interface AdminImgCustomRepository {

	// 공지 상세 - 해당 공지 게시글의 모든 이미지 경로 조회
	List<String> selectNoticeImgList(long noticeId);
}
