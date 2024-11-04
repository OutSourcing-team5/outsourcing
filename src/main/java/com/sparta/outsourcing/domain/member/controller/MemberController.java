package com.sparta.outsourcing.domain.member.controller;

import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(requestDto));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }



}
