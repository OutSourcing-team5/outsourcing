package com.sparta.outsourcing.domain.order.entity;

import com.sparta.outsourcing.domain.TimeStamped;
import com.sparta.outsourcing.domain.member.entity.Member;
import com.sparta.outsourcing.domain.menu.entity.Menu;
import com.sparta.outsourcing.domain.store.entity.Store;

import jakarta.persistence.Column;
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
import lombok.Setter;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor
public class Order extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Setter
	private OrderStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", nullable = false)
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id", nullable = false)
	private Menu menu;

	@Column(nullable = false)
	private boolean inactive;

	@Column(nullable = false)
	private int count;

	@Column(nullable = false)
	private int totalPrice;

	// 주문 생성자: 필수 필드 초기화
	private Order(Member member, Store store, Menu menu, int count) {
		this.member = member;
		this.store = store;
		this.menu = menu;
		this.count = count;
		this.totalPrice = menu.getPrice() * count;
		this.status = OrderStatus.PENDING;
		this.inactive = false;
	}

	public static Order createOf(Member member, Store store, Menu menu, int count) {
		return new Order(member, store, menu, count);
	}

	public void delete() {
		this.inactive = true;
	}
}
