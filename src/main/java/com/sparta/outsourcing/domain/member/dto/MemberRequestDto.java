package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.MemberRole;

import lombok.Getter;

@Getter
public class MemberRequestDto {
    private String userName;
    private String email;
    private String password;
    private String address;
    private MemberRole role;
}
