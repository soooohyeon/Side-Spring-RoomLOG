package com.example.roomlog.repository.community.image;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.image.QCommunityImg;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommunityImgCustomRepositoryImpl implements CommunityImgCustomRepository {
	
	private final JPAQueryFactory jpaQueryFactory;

	// 커뮤니티 상세 - 해당 커뮤니티 게시글의 모든 이미지 경로 조회
	public List<String> selectImgList(long communityId) {
		QCommunityImg img = QCommunityImg.communityImg;
		
		List<Tuple> lists = jpaQueryFactory
				.select(
					img.communityImgPath,
					img.communityImgUuid
				 )
				.from(img)
				.where(img.community.communityId.eq(communityId))
				.orderBy(img.communityImgId.asc())
				.fetch();
		
		// 경로 + 이미지명을 넣은 리스트로 변환
		List<String> imgUrls = lists.stream()
			.map(tuple -> tuple.get(img.communityImgPath) + "/" + tuple.get(img.communityImgUuid))
			.collect(Collectors.toList());
		 
		 return imgUrls;
	}
	
}