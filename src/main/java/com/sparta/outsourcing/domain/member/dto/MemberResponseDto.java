package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.Member;
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
    private String createdAt;
    private String modifiedAt;

    public MemberResponseDto(Member savedMember) {
        this.id = savedMember.getId();
        this.userName = savedMember.getUsername();
        this.email = savedMember.getEmail();
        this.password = savedMember.getPassword();
        this.address = savedMember.getAddress();
        this.role = savedMember.getRole();
        this.createdAt = savedMember.getCreatedAt().toString();
        this.modifiedAt = savedMember.getModifiedAt().toString();
    }
}
