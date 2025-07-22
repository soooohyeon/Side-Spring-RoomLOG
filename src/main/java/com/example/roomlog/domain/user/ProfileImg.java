package com.example.roomlog.domain.user;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table (name = "tbl_profile_img")
@Getter @ToString
@NoArgsConstructor
public class ProfileImg {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profileImgId;
	@Column(unique = true, nullable = false)
	private String profileImgUuid;
	@Column(nullable = false)
	private String profileImgOriginal;
	@Column(nullable = false)
	private String profileImgPath;
	
	@Builder
	public ProfileImg(String profileImgUuid, String profileImgOriginal, String profileImgPath) {
		this.profileImgUuid = profileImgUuid;
		this.profileImgOriginal = profileImgOriginal;
		this.profileImgPath = profileImgPath;
	}
	
	// 프로필 사진 수정 시
	public void updateProfileImg (String profileImgUuid, String profileImgOriginal, String profileImgPath) {
		this.profileImgUuid = profileImgUuid;
		this.profileImgOriginal = profileImgOriginal;
		this.profileImgPath = profileImgPath;
	}
	
}
