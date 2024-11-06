package com.sparta.outsourcing.domain.member.entity;

import com.sparta.outsourcing.domain.TimeStamped;

import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "members")
@NoArgsConstructor
public class Member extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Column(nullable = false)
	private int points;

	@Column(nullable = false)
	private boolean inactive;

	private Member(String username, String password, String email, String address, MemberRole role, int points) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.role = role;
		this.inactive = false;
		this.points = points;
	}

	public static Member createOf(String username, String password, String email, String address, MemberRole role, int points) {
		return new Member(username, password, email, address, role, points);
	}

	public void delete() {
		inactive = true;
	}

	public void update(String username, String password, String address) {
		if (username != null && !username.isEmpty()) {
			this.username = username;
		}
		if (password != null && !username.isEmpty()) {
			this.password = password;
		}
		if (address != null && !username.isEmpty()) {
			this.address = address;
		}
	}
}
