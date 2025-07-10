package com.example.roomlog.domain.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
	@NotNull
	@Column(unique = true, nullable = false)
	private String progileImgUuid;
	@NotNull
	@Column(nullable = false)
	private String profileImgOriginal;
	@NotNull
	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime profileImgUploadDate;
	
	@Builder
	public ProfileImg(String progileImgUuid, String profileImgOriginal) {
		this.progileImgUuid = progileImgUuid;
		this.profileImgOriginal = profileImgOriginal;
	}
	
}
