package com.example.roomlog.service.follow;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.roomlog.domain.follow.Follow;
import com.example.roomlog.dto.follow.FollowDTO;
import com.example.roomlog.repository.follow.FollowRepository;
import com.example.roomlog.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowService {
	
	private final FollowRepository followRepository;
	private final UserRepository userRepository;
	
	// 팔로우
	public void insertFollow (long fromUserId, long toUserId) {
		long check = followRepository.checkFollow(fromUserId, toUserId);
		
		if (fromUserId != toUserId && check == 0) {
			Follow follow = Follow.builder()
					.fromUser(userRepository.findByUserId(fromUserId).get())
					.toUser(userRepository.findByUserId(toUserId).get())
					.build();
			followRepository.save(follow);
		}
	}
	
	// 팔로우 취소
	public void cancelFollow (long fromUserId, long toUserId) {
		followRepository.cancelFollow(fromUserId, toUserId);
	}
	
	// 내가 팔로우한 유저 수
	public int countFollow (long userId) {
		return followRepository.countFollow(userId);
	}
	
	// 나를 팔로우한 팔로워 수
	public int countFollower (long userId) {
		return followRepository.countFollower(userId);
	}
	
	// 해당 유저의 팔로워 목록
	public List<FollowDTO> selectFollowerList (long userId, String keyword) {
		List<FollowDTO> lists = followRepository.selectFollowerList(userId, keyword);
		for (FollowDTO follow : lists) {
			long isFollowed = followRepository.checkFollow(userId, follow.getUserId());
			follow.setFollowed(isFollowed == 1);
		}
		
		return lists;
	}
	
	// 해당 유저가 팔로우한 유저 목록
	public List<FollowDTO> selectFollowList (long userId, String keyword) {
		return followRepository.selectFollowList(userId, keyword);
	}
}
