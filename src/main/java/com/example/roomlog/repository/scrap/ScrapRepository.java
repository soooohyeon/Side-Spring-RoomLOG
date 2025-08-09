package com.example.roomlog.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.scrap.Scrap;
import com.example.roomlog.domain.scrap.ScrapId;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, ScrapId>, ScrapCustomRepository {

	// 마이페이지 - 해당 유저가 작성한 커뮤니티 게시글 개수
	@Query("SELECT COUNT(s) FROM Scrap s " +
			"	WHERE s.user.userId = :userId")
	int countScrapListByUser(long userId);
	
	// 스크랩 취소
	public void deleteByScrapId(ScrapId scrapId);
	
}
