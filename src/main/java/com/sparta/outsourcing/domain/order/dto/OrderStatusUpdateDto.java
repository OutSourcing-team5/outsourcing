package com.sparta.outsourcing.domain.order.dto;

import java.time.LocalDateTime;

import com.sparta.outsourcing.domain.order.entity.OrderStatusUpdate;

import lombok.Getter;

@Getter
public class OrderStatusUpdateDto {
	private Long orderId;
	private String newStatus;
	private LocalDateTime createdAt;

	public OrderStatusUpdateDto(OrderStatusUpdate orderStatusUpdate) {
		this.orderId = orderStatusUpdate.getOrderId();
		this.newStatus = orderStatusUpdate.getNewStatus();
		this.createdAt = orderStatusUpdate.getCreatedAt();
	}
}
