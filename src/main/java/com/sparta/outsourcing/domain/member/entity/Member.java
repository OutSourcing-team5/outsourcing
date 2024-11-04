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

	public Member(MemberRequestDto requestDto) {
		this.username = requestDto.getUserName();
		this.password = requestDto.getPassword();
		this.email = requestDto.getEmail();
		this.address = requestDto.getAddress();
		this.role = requestDto.getRole();
	}

	public MemberResponseDto to() {
		return new MemberResponseDto(
				this.id,
				this.username,
				this.email,
				this.password,
				this.address,
				this.role
		);
	}
}
