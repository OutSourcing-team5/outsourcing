package com.sparta.outsourcing.domain.member.controller;


import com.sparta.outsourcing.domain.member.service.MemberService;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@NoArgsConstructor
public class MemberController {
    private MemberService memberService;

    @DeleteMapping("/api/members/{memberId}")
    public ResponseEntity<String> delete(@PathVariable Long memberId) {
        memberService.delete(memberId);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }
}
