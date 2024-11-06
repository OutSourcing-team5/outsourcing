package com.sparta.outsourcing.domain.dashboard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.dashboard.dto.DailyResponseDto;
import com.sparta.outsourcing.domain.dashboard.dto.MonthlyResponseDto;
import com.sparta.outsourcing.domain.dashboard.service.StoreDashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/{storeId}/dashboard")
public class StoreDashboardController {
	private final StoreDashboardService storeDashboardService;

	// 일간 통계 조회하기
	@GetMapping("/day")
	public ResponseEntity<DailyResponseDto> getDaily(@PathVariable Long storeId,
		@RequestParam LocalDate date) {

		DailyResponseDto responseDto = StoreDashboardService.getDailyStatistics(storeId, date);
		return ResponseEntity.ok(responseDto);
	}

	// 월간 통계 조회하기
	@GetMapping("/month")
	public ResponseEntity<MonthlyResponseDto> getMonthly(@PathVariable Long storeId,
		@RequestParam String yearMonth) {
		MonthlyResponseDto responseDto = storeDashboardService.getMonthlyStatistics(storeId, yearMonth);
		return ResponseEntity.ok(responseDto);

	}
}