package com.sparta.outsourcing.domain.order.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing.domain.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderResponseDto> createOrder(
		@RequestBody OrderRequestDto requestDto,
		@RequestAttribute("id") Long memberId) {

		OrderResponseDto responseDto = orderService.createOrder(requestDto, memberId);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
	}
}
