package com.sparta.outsourcing.domain.order.entity;

import java.time.LocalDateTime;

import com.sparta.outsourcing.domain.TimeStamped;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class OrderStatusUpdate extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long orderId;
	private Long memberId;
	private String newStatus;

	public OrderStatusUpdate(Long orderId, Long memberId, String newStatus) {
		this.orderId = orderId;
		this.memberId = memberId;
		this.newStatus = newStatus;
	}
}
