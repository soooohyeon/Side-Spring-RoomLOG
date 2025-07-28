package com.example.roomlog.domain.community.hashtag;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table (name = "tbl_hashtag")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long hashtagId;
	@Column(unique = true, nullable = false)
	private String hashtagName;
	
	@Builder
	public Hashtag(String hashtagName) {
		this.hashtagName = hashtagName;
	}
	
}
