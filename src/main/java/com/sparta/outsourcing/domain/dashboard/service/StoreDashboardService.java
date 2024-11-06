package com.sparta.outsourcing.domain.dashboard.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.dashboard.dto.DailyResponseDto;
import com.sparta.outsourcing.domain.dashboard.dto.MonthlyResponseDto;

@Service
public class StoreDashboardService {
	public static DailyResponseDto getDailyStatistics(Long storeId, LocalDate date) {
	}

	public MonthlyResponseDto getMonthlyStatistics(Long storeId, String yearMonth) {
	}
}
