package com.sparta.outsourcing.domain.order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sparta.outsourcing.domain.order.dto.OrderStatusUpdateDto;
import com.sparta.outsourcing.domain.order.entity.OrderStatusUpdate;
import com.sparta.outsourcing.domain.order.repository.OrderStatusUpdateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
	private final OrderStatusUpdateRepository orderStatusUpdateRepository;

	public List<OrderStatusUpdateDto> getNotification(Long memberId) {
		List<OrderStatusUpdate> updateStatusList = orderStatusUpdateRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
		return updateStatusList.stream()
			.map(OrderStatusUpdateDto::new)
			.collect(Collectors.toList());
	}
}
