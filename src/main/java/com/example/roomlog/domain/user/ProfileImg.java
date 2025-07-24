package com.example.roomlog.domain.user;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User user;
	
	@Column(unique = true, nullable = false)
	private String profileImgUuid;
	@Column(nullable = false)
	private String profileImgOriginal;
	@Column(nullable = false)
	private String profileImgPath;
	
	@Builder
	public ProfileImg(User user, String profileImgUuid, String profileImgOriginal, String profileImgPath) {
		this.user = user;
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
