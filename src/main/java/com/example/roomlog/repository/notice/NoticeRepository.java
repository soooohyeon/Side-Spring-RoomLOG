package com.example.roomlog.repository.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.notice.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>, NoticeCustomRepository {

	
	
}
