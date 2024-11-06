package com.sparta.outsourcing.domain.order.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByMemberAndInactiveFalse(Member member);

	// 일간 주문 수 조회
	int countByStoreIdAndOrderDate(Long storeId, LocalDate date);

	// 월간 주문 수 조회
	int countByStoreIdAndOrderDateBetween(Long storeId, LocalDate start, LocalDate end);

	// 일간 매출 합계 조회
	int sumTotalPriceByStoreIdAndOrderDate(Long storeId, LocalDate date);

	// 월간 매출 합계 조회
	int sumTotalPriceByStoreIdAndOrderDateBetween(Long storeId, LocalDate start, LocalDate end);
}

