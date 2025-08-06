package com.example.roomlog.repository.adminImg;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.roomlog.domain.adminImg.AdminImg;

public interface AdminImgRepository extends JpaRepository<AdminImg, Long>, AdminImgCustomRepository {

}
