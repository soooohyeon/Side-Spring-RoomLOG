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
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "tbl_profile_img")
@Getter @Setter @ToString
@NoArgsConstructor
public class ProfileImg {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profileImgId;
	@Column(unique = true, nullable = false)
	private String progileImgUuid;
	@Column(nullable = false)
	private String profileImgOriginal;
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime profileImgUploadDate;
	
	@Builder
	public ProfileImg(String progileImgUuid, String profileImgOriginal) {
		this.progileImgUuid = progileImgUuid;
		this.profileImgOriginal = profileImgOriginal;
	}
	
}
