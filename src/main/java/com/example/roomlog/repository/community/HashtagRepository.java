package com.example.roomlog.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
	
	// 해시태그 목록 (검색 조건 포함)
	List<Hashtag> findByHashtagNameContaining(String keyword);
	
}
