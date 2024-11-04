package com.sparta.outsourcing.domain.order.dto;

import com.sparta.outsourcing.domain.order.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
	private Long orderId;
	private String status;
	private String menuId;
	private String storeId;
	private String createdAt;


	public OrderResponseDto(Order order) {
		this.orderId = order.getId();
		this.menuId = order.getMenu().getMenuName();
		this.storeId = order.getStore().getStoreName();
		this.status = order.getStatus().name();  // Enum 값을 문자열로 변환
		this.createdAt = order.getCreatedAt().toString();  // 타임스탬프를 문자열로 변환
	}
}
