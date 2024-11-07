package com.sparta.outsourcing.domain.order.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto {
	private Long menuId;
	private Long storeId;
	private int count;
	private List<Long> optionIds;
}
