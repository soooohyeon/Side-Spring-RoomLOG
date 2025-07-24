package com.example.roomlog.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.community.CommunityHashtag;
import com.example.roomlog.domain.community.CommunityHashtagId;

@Repository
public interface CommunityHashtagRepository extends JpaRepository<CommunityHashtag, CommunityHashtagId> {

	
}
