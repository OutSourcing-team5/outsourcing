package com.sparta.outsourcing.domain.dashboard.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.outsourcing.domain.dashboard.dto.DailyResponseDto;
import com.sparta.outsourcing.domain.dashboard.dto.MonthlyResponseDto;
import com.sparta.outsourcing.domain.order.entity.Order;
import com.sparta.outsourcing.domain.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreDashboardService {

	private final OrderRepository orderRepository;

	// 일간 고객 수와 매출량 조회
	@Transactional(readOnly = true)
	public DailyResponseDto getDailyStatistics(Long storeId, LocalDate date) {
		LocalDateTime startOfDay = date.atStartOfDay();
		LocalDateTime endOfDay = date.atTime(23, 59, 59);

		List<Order> dailyOrders = orderRepository.findAllByStoreIdAndCreatedAtBetween(storeId, startOfDay, endOfDay);

		int dailyOrderCount = dailyOrders.size();
		int dailySalesAmount = dailyOrders.stream().mapToInt(Order::getTotalPrice).sum();

		return new DailyResponseDto(dailyOrderCount, dailySalesAmount);
	}

	// 월간 고객 수와 매출량 조회
	@Transactional(readOnly = true)
	public MonthlyResponseDto getMonthlyStatistics(Long storeId, String yearMonth) {
		YearMonth month = YearMonth.parse(yearMonth, DateTimeFormatter.ofPattern("yyyy-MM"));
		LocalDateTime startOfMonth = month.atDay(1).atStartOfDay();
		LocalDateTime endOfMonth = month.atEndOfMonth().atTime(23, 59, 59);

		List<Order> monthlyOrders = orderRepository.findAllByStoreIdAndCreatedAtBetween(storeId, startOfMonth, endOfMonth);

		int monthlyOrderCount = monthlyOrders.size();
		int monthlySalesAmount = monthlyOrders.stream().mapToInt(Order::getTotalPrice).sum();

		return new MonthlyResponseDto(monthlyOrderCount, monthlySalesAmount);
	}
}
