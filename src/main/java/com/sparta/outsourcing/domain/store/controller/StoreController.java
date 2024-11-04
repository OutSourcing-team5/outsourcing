package com.sparta.outsourcing.domain.store.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.store.dto.DetailedStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.ShortStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping("")
	public ResponseEntity<StoreResponseDto> createStore(
		@RequestBody StoreRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			storeService.createStore(requestDto, memberId)
		);
	}

	@GetMapping("")
	public ResponseEntity<Page<ShortStoreResponseDto>> getStore(
		@RequestParam(required = false) String storeName,
		@RequestParam(defaultValue = "0") int page
	) {
		if (storeName != null && !storeName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(storeService.getAllStoreByName(storeName, page));
		}
		return ResponseEntity.status(HttpStatus.OK).body(storeService.getAllStore(page));
	}

	@GetMapping("/{storeId}")
	public ResponseEntity<DetailedStoreResponseDto> getOneStore(
		@PathVariable Long storeId
	) {
		return ResponseEntity.status(HttpStatus.OK).body(storeService.getOneStore(storeId));
	}
}
