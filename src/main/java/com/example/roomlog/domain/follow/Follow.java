package com.example.roomlog.domain.follow;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.roomlog.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "tbl_follow")
@Getter @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long followId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User fromUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "to_user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
	private User toUser;
	
	@Column(nullable = false)
	@CreatedDate
	private LocalDateTime followDate;

	@Builder
	public Follow(User fromUser, User toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
	}
	
}
