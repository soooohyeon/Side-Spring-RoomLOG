package com.example.roomlog.domain.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter	// 자식 클래스에서 사용하는 것은 추상클래스에 getter 반영 안됨
public abstract class BaseTimeEntity {
	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;
	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
