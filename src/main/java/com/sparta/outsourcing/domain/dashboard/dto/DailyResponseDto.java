package com.sparta.outsourcing.domain.dashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DailyResponseDto {
	private int dailyOrderCount;
	private int dailySalesAmount;
}
