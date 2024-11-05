package com.sparta.outsourcing.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequestDto {
	@Email(message = "이메일 형식에 맞게 작성하세요.")
	private String email;
	@NotNull(message = "비밀번호를 입력하세요")
	private String password;
}
