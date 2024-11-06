package com.sparta.outsourcing.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateMemberRequestDto {
	private Long memberId;
	private String username;
	@NotNull(message = "비밀번호를 입력하세요")
	private String oldPassword;
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
		message = "숫자, 문자, 특수문자가 1개 이상 사용되어야 함, 길이: 8 ~ 16")
	private String newPassword;
	private String address;
}
