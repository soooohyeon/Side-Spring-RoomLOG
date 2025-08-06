package com.example.roomlog.domain.adminImg;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.banner.Banner;
import com.example.roomlog.domain.notice.Notice;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_admin_img")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminImg {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long adminImgId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "noticeId")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private Notice notice;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bannerId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Banner banner;
	
	@Column(nullable = false)
	private String adminImgOriginal;
	@Column(unique = true, nullable = false)
	private String adminImgUuid;
	@Column(nullable = false)
	private String adminImgPath;
	
	@Builder
	public AdminImg(Notice notice, Banner banner, String adminImgOriginal, String adminImgUuid,
			String adminImgPath) {
		this.notice = notice;
		this.banner = banner;
		this.adminImgOriginal = adminImgOriginal;
		this.adminImgUuid = adminImgUuid;
		this.adminImgPath = adminImgPath;
	}
	
}
