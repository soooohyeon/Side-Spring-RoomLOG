package com.example.roomlog.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.community.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {
	
}
