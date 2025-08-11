package com.example.roomlog.repository.user;

import java.util.List;

import com.example.roomlog.dto.user.UserDTO;
import com.example.roomlog.dto.user.UserInfoDTO;

public interface UserCustomRepository {
	
	// 메인 - 팔로우 많은 순 상위 4명의 유저
	List<UserDTO> selectFollowRankingList();

	// 마이페이지 (메인) - 유저 정보 출력
	UserInfoDTO selectUser(long userId);
	
	// 마이페이지 (설정) - 수정할 유저 정보 출력
	UserInfoDTO selectEditUser(long userId);
	
}
