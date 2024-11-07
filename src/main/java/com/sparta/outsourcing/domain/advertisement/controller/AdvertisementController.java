package com.sparta.outsourcing.domain.advertisement.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.advertisement.dto.StoreAdvertisementRequestDto;
import com.sparta.outsourcing.domain.advertisement.service.AdvertisementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores/advertisements")
public class AdvertisementController {

	private final AdvertisementService advertisementService;

	@PostMapping("/register")
	public ResponseEntity<Void> registerAdvertisement(
		@RequestBody StoreAdvertisementRequestDto advertisementRequestDto,
		@RequestAttribute("id") Long memberId
	) {
		advertisementService.registerAdvertisement(advertisementRequestDto, memberId);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/unregister")
	public ResponseEntity<Void> unregisterAdvertisement(
		@RequestBody StoreAdvertisementRequestDto advertisementRequestDto,
		@RequestAttribute("id") Long memberId
	) {
		advertisementService.unregisterAdvertisement(advertisementRequestDto, memberId);
		return ResponseEntity.noContent().build();
	}

}
