package com.sparta.outsourcing.domain.member.controller;

import com.sparta.outsourcing.common.filter.AuthFilter;
import com.sparta.outsourcing.domain.member.dto.AddPointsRequestDto;
import com.sparta.outsourcing.domain.member.dto.DeleteMemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.LoginRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.dto.UpdateMemberRequestDto;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
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
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signup(requestDto));
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        memberService.login(requestDto, response);
    }

    @DeleteMapping("/members")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(
        @RequestBody DeleteMemberRequestDto requestDto,
        @RequestAttribute("id") Long memberId
    ) {
        memberService.deleteMember(requestDto, memberId);
    }

    @PutMapping("/members")
    public void updateMember(
        @RequestBody @Valid UpdateMemberRequestDto requestDto,
        @RequestAttribute("id") Long memberId
    ) {
        memberService.updateMember(requestDto, memberId);
    }

    @PutMapping("/members/points")
    public ResponseEntity<MemberResponseDto> addPoints(
        @RequestBody @Valid AddPointsRequestDto requestDto,
        @RequestAttribute("id") Long currentMemberId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.addPoints(requestDto, currentMemberId));
    }
}
