package com.sparta.outsourcing.domain.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findAllByMember(Member member);

	@Query("SELECT o FROM Order o WHERE o.member = :member AND o.isDeleted = false")
	List<Order> findAllByActiveMember(Member member);
}
