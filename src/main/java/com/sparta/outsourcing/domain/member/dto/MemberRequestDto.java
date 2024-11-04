package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    @NotNull(message = "이름을 입력하세요.")
    private String userName;
    @Email(message = "이메일 형식에 맞게 작성하세요.")
    private String email;
    @NotNull(message = "비밀번호를 입력하세요")
    private String password;
    @NotNull(message = "주소를 입력하세요")
    private String address;
    private MemberRole role;
}
