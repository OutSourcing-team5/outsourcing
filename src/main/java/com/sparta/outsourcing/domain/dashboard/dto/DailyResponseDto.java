package com.sparta.outsourcing.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class DailyResponseDto {
	private int dailyOrderCount;
	private int dailySalesAmount;


}
