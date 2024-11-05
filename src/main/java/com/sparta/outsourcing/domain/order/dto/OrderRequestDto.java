package com.sparta.outsourcing.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
	private Long menuId;
	private Long storeId;
	private int count;
}
