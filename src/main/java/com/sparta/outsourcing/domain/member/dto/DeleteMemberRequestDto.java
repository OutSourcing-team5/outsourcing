package com.sparta.outsourcing.domain.member.dto;

import lombok.Getter;

@Getter
public class DeleteMemberRequestDto {
	private Long memberId;
	private String oldPassword;
}
