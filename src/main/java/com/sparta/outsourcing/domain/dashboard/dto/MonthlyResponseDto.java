package com.sparta.outsourcing.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class MonthlyResponseDto {
	private int monthlyOrderCount;
	private int monthlySalesCount;

}
