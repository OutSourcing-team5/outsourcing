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
import com.sparta.outsourcing.domain.order.service.NotificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {
	private final NotificationService notificationService;

	@GetMapping
	public ResponseEntity<List<OrderStatusUpdateDto>> getNotification(@RequestAttribute("id") Long memberId) {
		List<OrderStatusUpdateDto> response = notificationService.getNotification(memberId);
		return ResponseEntity.ok(response);
	}
}
