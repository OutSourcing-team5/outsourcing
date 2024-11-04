package com.sparta.outsourcing.domain.store.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.member.entity.MemberRole;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StoreController {

	private final StoreService storeService;

	@PostMapping("/stores")
	public ResponseEntity<StoreResponseDto> createStore(
		@RequestBody StoreRequestDto requestDto,
		@RequestAttribute("email") String email
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			storeService.createStore(email, requestDto)
		);
	}
}
