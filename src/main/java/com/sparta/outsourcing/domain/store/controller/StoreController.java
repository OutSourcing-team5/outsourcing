package com.sparta.outsourcing.domain.store.controller;

import java.util.List;

import org.apache.coyote.Request;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.store.dto.CategoryStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.DetailedStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.ShortStoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreRequestDto;
import com.sparta.outsourcing.domain.store.dto.StoreResponseDto;
import com.sparta.outsourcing.domain.store.dto.StoreUpdateRequestDto;
import com.sparta.outsourcing.domain.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;

	@PostMapping("")
	public ResponseEntity<StoreResponseDto> createStore(
		@Valid @RequestBody StoreRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			storeService.createStore(requestDto, memberId)
		);
	}

	@GetMapping("")
	public ResponseEntity<List<ShortStoreResponseDto>> getStore(
		@RequestParam(required = false) String storeName,
		@RequestParam(defaultValue = "0") int page,
		@RequestAttribute("id") Long memberId
	) {
		if (storeName != null && !storeName.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(storeService.getAllStoreByName(storeName, page, memberId));
		}
		return ResponseEntity.status(HttpStatus.OK).body(storeService.getAllStore(page, memberId));
	}


	@GetMapping("/{storeId}")
	public ResponseEntity<DetailedStoreResponseDto> getOneStore(
		@PathVariable Long storeId
	) {
		return ResponseEntity.status(HttpStatus.OK).body(storeService.getOneStore(storeId));
	}

	@GetMapping("/{storeId}/category")
	public ResponseEntity<CategoryStoreResponseDto> getOneStoreCategory(
		@PathVariable Long storeId,
		@RequestParam(required = false) String category
	) {
		return ResponseEntity.status(HttpStatus.OK).body(storeService.getOneStoreCategory(storeId, category));
	}

	@PutMapping("/{storeId}")
	public ResponseEntity<StoreResponseDto> updateStore(
		@PathVariable Long storeId,
		@Valid @RequestBody StoreUpdateRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			storeService.updateStore(storeId, requestDto, memberId)
		);
	}

	@DeleteMapping("/{storeId}")
	public ResponseEntity<Void> deleteStore(
		@PathVariable Long storeId,
		@RequestAttribute("id") Long memberId
	) {
		storeService.deleteStore(storeId, memberId);
		return ResponseEntity.noContent().build();
	}
}
