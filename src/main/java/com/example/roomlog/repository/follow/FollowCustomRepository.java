package com.example.roomlog.repository.follow;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.example.roomlog.dto.follow.FollowDTO;

public interface FollowCustomRepository {
	
	// 해당 유저가 팔로우한 유저 목록
	List<FollowDTO> selectFollowList(long userId, String keyword);
	
	// 해당 유저의 팔로워 목록
	List<FollowDTO> selectFollowerList(long userId, String keyword);

}
