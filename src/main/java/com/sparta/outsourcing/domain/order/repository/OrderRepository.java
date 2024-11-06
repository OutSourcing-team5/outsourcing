package com.sparta.outsourcing.domain.order.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByMemberAndInactiveFalse(Member member);

	List<Order> findAllByStoreIdAndCreatedAtBetween(Long storeId, LocalDateTime start, LocalDateTime end);
}

