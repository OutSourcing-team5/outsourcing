package com.sparta.outsourcing.domain.member.repository;

import com.sparta.outsourcing.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
