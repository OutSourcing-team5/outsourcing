package com.sparta.outsourcing.domain.menu.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.menu.dto.MenuRequestDto;
import com.sparta.outsourcing.domain.menu.dto.MenuResponseDto;
import com.sparta.outsourcing.domain.menu.dto.OptionRequestDto;
import com.sparta.outsourcing.domain.menu.dto.OptionResponse;
import com.sparta.outsourcing.domain.menu.service.OptionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/options")
@RequiredArgsConstructor
public class OptionController {
	private final OptionService optionService;

	@PostMapping("")
	public ResponseEntity<OptionResponse> createMenu(
		@Valid @RequestBody OptionRequestDto optionRequestDto,
		@RequestAttribute("id") Long currentMemberId
	) {
		OptionResponse optionResponse = optionService.createOption(optionRequestDto, currentMemberId);

		return ResponseEntity.status(HttpStatus.CREATED).body(optionResponse);
	}

	@PutMapping("/{optionId}")
	public ResponseEntity<OptionResponse> updateOption(
		@PathVariable Long optionId,
		@Valid @RequestBody OptionRequestDto optionRequestDto
	) {
		OptionResponse optionResponse = optionService.updateOption(optionId, optionRequestDto);
		return ResponseEntity.ok(optionResponse);
	}

	@DeleteMapping("/{optionId}")
	public ResponseEntity<Void> deleteOption(@PathVariable Long optionId) {
		optionService.deleteOption(optionId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
