package com.sparta.outsourcing.domain.dashboard.controller;

import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sparta.outsourcing.domain.dashboard.dto.DailyResponseDto;
import com.sparta.outsourcing.domain.dashboard.dto.MonthlyResponseDto;
import com.sparta.outsourcing.domain.dashboard.service.StoreDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/dashboard")
public class StoreDashboardController {

	private final StoreDashboardService storeDashboardService;

	// 일간 통계 조회 엔드포인트
	@GetMapping("/daily")
	public ResponseEntity<DailyResponseDto> getDailyStatistics(
		@PathVariable Long storeId,
		@RequestParam("date") LocalDate date) {

		DailyResponseDto responseDto = storeDashboardService.getDailyStatistics(storeId, date);
		return ResponseEntity.ok(responseDto);
	}

	// 월간 통계 조회 엔드포인트
	@GetMapping("/monthly")
	public ResponseEntity<MonthlyResponseDto> getMonthlyStatistics(
		@PathVariable Long storeId,
		@RequestParam("yearMonth") String yearMonth) {

		MonthlyResponseDto responseDto = storeDashboardService.getMonthlyStatistics(storeId, yearMonth);
		return ResponseEntity.ok(responseDto);
	}
}
