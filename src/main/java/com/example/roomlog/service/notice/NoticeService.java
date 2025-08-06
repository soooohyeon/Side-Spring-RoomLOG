package com.example.roomlog.service.notice;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.roomlog.dto.notice.NoticeDTO;
import com.example.roomlog.dto.page.Criteria;
import com.example.roomlog.repository.notice.NoticeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	// 공지 게시글 총 개수
	public int countAllNotice() {
		return (int) noticeRepository.count();
	}
	
	// 공지 게시글 목록
	public List<NoticeDTO> selectListAll(Criteria criteria) {
		return noticeRepository.selectListWithPaging(criteria);
	}
	
	// 공지 등록
	public void insertNotice(NoticeDTO noticeDTO, List<MultipartFile> images) {
		
	}
	
}
