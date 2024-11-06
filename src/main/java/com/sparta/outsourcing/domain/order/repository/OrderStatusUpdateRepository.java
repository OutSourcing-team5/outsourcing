package com.sparta.outsourcing.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.outsourcing.domain.order.entity.OrderStatusUpdate;

public interface OrderStatusUpdateRepository extends JpaRepository<OrderStatusUpdate, Long> {
	List<OrderStatusUpdate> findAllByMemberIdOrderByCreatedAtDesc(Long memberId);
}
