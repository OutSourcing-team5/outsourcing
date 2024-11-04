package com.sparta.outsourcing.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class OrderStatusRequestDto {
	private Long orderId;
	private String orderStatus;
}
