package com.example.roomlog.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.roomlog.domain.user.SocialType;

@Repository
public interface SocialTypeRepository extends JpaRepository<SocialType, Integer> {

}
