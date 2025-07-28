package com.example.roomlog.service.follow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.roomlog.domain.follow.Follow;
import com.example.roomlog.repository.follow.FollowRepository;
import com.example.roomlog.repository.user.UserRepository;

@Service
public class FollowService {
	
	@Autowired
	FollowRepository followRepository;
	@Autowired
	UserRepository userRepository;
	
	// 팔로우
	public void insertFollow(long fromUserId, long toUserId) {
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
	public void cancelFollow(long fromUserId, long toUserId) {
		followRepository.cancelFollow(fromUserId, toUserId);
	}
	
}
