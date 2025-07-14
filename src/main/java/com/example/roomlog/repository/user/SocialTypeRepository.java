package com.example.roomlog.repository.user;

import org.hibernate.annotations.processing.Find;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.user.SocialType;

@Repository
public interface SocialTypeRepository extends JpaRepository<SocialType, Integer> {
	// 소설 타입 ID 구하기
	public SocialType findBySocialTypeName (String socialTypeName);
}
