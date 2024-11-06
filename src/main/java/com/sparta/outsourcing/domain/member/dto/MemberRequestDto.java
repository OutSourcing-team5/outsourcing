package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.MemberRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class MemberRequestDto {
    @NotNull(message = "이름을 입력하세요.")
    private String username;
    @Email(message = "이메일 형식에 맞게 작성하세요.")
    private String email;
    @NotNull(message = "비밀번호를 입력하세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
        message = "숫자, 문자, 특수문자가 1개 이상 사용되어야 함, 길이: 8 ~ 16")
    private String password;
    @NotNull(message = "주소를 입력하세요")
    private String address;
    private MemberRole role;
}
