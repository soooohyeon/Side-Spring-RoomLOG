package com.example.roomlog.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_social_type")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialType {
	@Id
	private int socialTypeId;
	@Column(unique = true)
	private String socialTypeName;

    @Builder
    public SocialType(int socialTypeId, String socialTypeName) {
        this.socialTypeId = socialTypeId;
        this.socialTypeName = socialTypeName;
    }
}
