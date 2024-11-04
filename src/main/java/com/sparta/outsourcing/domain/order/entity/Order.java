package com.sparta.outsourcing.domain.order.entity;

import com.sparta.outsourcing.domain.TimeStamped;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storeId", nullable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menuId", nullable = false)
	private Menu menu;

	// 주문 생성자: 필수 필드 초기화
	private Order(Member member, Store store, Menu menu) {
		this.member = member;
		this.store = store;
		this.menu = menu;
		this.status = OrderStatus.PENDING;
	}

	public static Order createOf(Member member, Store store, Menu menu) {
		return new Order(member, store, menu);
	}
}
