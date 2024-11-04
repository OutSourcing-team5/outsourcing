package com.sparta.outsourcing.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByMember(Member member);
}
