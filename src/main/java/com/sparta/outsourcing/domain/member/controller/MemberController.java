package com.sparta.outsourcing.domain.member.controller;

import com.sparta.outsourcing.common.filter.AuthFilter;
import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final AuthFilter authFilter;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(requestDto));
    }
    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@RequestBody MemberRequestDto requestDto, HttpServletResponse response) {
        memberService.login(requestDto, response);
    }
}
