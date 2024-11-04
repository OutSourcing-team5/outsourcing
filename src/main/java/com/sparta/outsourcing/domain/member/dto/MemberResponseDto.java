package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String userName;
    private String email;
    private String password;
    private String address;
    private MemberRole role;
}
