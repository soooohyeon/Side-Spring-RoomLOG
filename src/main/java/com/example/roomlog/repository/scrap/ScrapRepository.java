package com.example.roomlog.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.Scrap;
import com.example.roomlog.domain.community.ScrapId;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, ScrapId>, ScrapCustomRepository {
	
	// 스크랩 취소
	public void deleteByScrapId(ScrapId scrapId);
	
}
