package com.example.roomlog.repository.user;

import java.util.List;

import com.example.roomlog.dto.user.UserDTO;

public interface UserCustomRepository {
	
	// 메인 - 팔로우 많은 순 상위 4명의 유저
	List<UserDTO> selectFollowRankingList();

}
