package com.sparta.outsourcing.domain.member.dto;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String username;
    private String email;
    private int points;
    private String createdAt;
    private String modifiedAt;

    public MemberResponseDto(Member savedMember) {
        this.id = savedMember.getId();
        this.username = savedMember.getUsername();
        this.email = savedMember.getEmail();
        this.points = savedMember.getPoints();
        this.createdAt = savedMember.getCreatedAt().toString();
        this.modifiedAt = savedMember.getModifiedAt().toString();
    }
}
