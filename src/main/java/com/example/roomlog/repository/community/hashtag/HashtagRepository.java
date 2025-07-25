package com.example.roomlog.repository.community.hashtag;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.Hashtag;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long>, HashtagCustomRepository {

	// 해시태그 목록 (검색 조건 포함)
	List<Hashtag> findByHashtagNameContaining(String keyword);
	
	// 해시태그 존재 여부 확인
	Optional<Hashtag> findByHashtagName(String hashtag);
	
}
