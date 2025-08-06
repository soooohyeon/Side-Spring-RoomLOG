package com.example.roomlog.domain.banner;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.roomlog.domain.common.BaseTimeEntity;
import com.example.roomlog.domain.user.User;

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
@Table(name = "tbl_banner")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bannerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User adminUser;

	@Column(nullable = false)
	private String bannerTitle;
	@Column(length = 2000, nullable = false)
	private String bannerMemo;
	
	@Builder
	public Banner(User adminUser, String bannerTitle, String bannerMemo) {
		this.adminUser = adminUser;
		this.bannerTitle = bannerTitle;
		this.bannerMemo = bannerMemo;
	}
	
}
