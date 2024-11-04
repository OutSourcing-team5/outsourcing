package com.sparta.outsourcing.domain.member.service;

import java.util.Optional;

import com.sparta.outsourcing.common.JwtUtil;
import com.sparta.outsourcing.common.PasswordEncoder;
import com.sparta.outsourcing.domain.member.dto.MemberRequestDto;
import com.sparta.outsourcing.domain.member.dto.MemberResponseDto;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public MemberResponseDto signup(MemberRequestDto requestDto) {
        //email 중복확인
        Optional<Member> checkEmail = memberRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }
        // 비밀번호 검증
        if (!PasswordEncoder.verifyPassword(requestDto.getPassword())) {
            throw new IllegalArgumentException("비밀번호의 형식이 올바르지 않습니다.");
        }
        // 사용자 등록
        Member member = new Member(requestDto);
        Member savedMember = memberRepository.save(member);

        return savedMember.to();
    }
}
