package com.sparta.outsourcing.domain.order.dto;

import com.sparta.outsourcing.domain.order.entity.Order;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
	private Long id;
	private String status;
	private String menuName;
	private int price;
	private String createdAt;
	private String modifiedAt;


	public OrderResponseDto(Order order) {
		this.id = order.getId();
		this.status = order.getStatus().name();
		this.menuName = order.getMenu().getMenuName();
		this.price = order.getMenu().getPrice();
		this.createdAt = order.getCreatedAt().toString();
		this.modifiedAt = order.getModifiedAt().toString();
	}
}
