package com.sparta.outsourcing.domain.order.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.order.dto.OrderStatusUpdateDto;
import com.sparta.outsourcing.domain.order.entity.OrderStatusUpdate;
import com.sparta.outsourcing.domain.order.repository.OrderStatusUpdateRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
	private final OrderStatusUpdateRepository orderStatusUpdateRepository;

	@GetMapping
	public ResponseEntity<List<OrderStatusUpdateDto>> getNotification(@RequestAttribute("id") Long memberId) {
		List<OrderStatusUpdate> updates = orderStatusUpdateRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId);
		List<OrderStatusUpdateDto> response = updates.stream()
			.map(OrderStatusUpdateDto::new)
			.collect(Collectors.toList());
		return ResponseEntity.ok(response);
	}
}
