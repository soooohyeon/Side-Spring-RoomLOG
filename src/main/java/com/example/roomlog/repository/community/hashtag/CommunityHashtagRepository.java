package com.example.roomlog.repository.community.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.community.hashtag.CommunityHashtag;
import com.example.roomlog.domain.community.hashtag.CommunityHashtagId;

public interface CommunityHashtagRepository extends JpaRepository<CommunityHashtag, CommunityHashtagId> {

}
