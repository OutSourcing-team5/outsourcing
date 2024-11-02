package com.sparta.outsourcing.domain.member.service;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void delete(Long memberId) {
        Member member = new Member();
        memberRepository.deleteById(member.getId());
    }
}
