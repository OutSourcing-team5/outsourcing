package com.sparta.outsourcing.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateMemberRequestDto {
	@NotNull(message = "이름을 입력하세요.")
	private String userName;
	@NotNull(message = "비밀번호를 입력하세요")
	private String oldPassword;
	private String newPassword;
	@NotNull(message = "주소를 입력하세요")
	private String address;
}
