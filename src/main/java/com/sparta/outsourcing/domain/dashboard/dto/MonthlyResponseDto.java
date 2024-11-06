package com.sparta.outsourcing.domain.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MonthlyResponseDto {
	private int monthlyOrderCount;
	private int monthlySalesCount;

}
