package com.sparta.outsourcing.domain.review.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.dto.ReviewStoreResponseDto;
import com.sparta.outsourcing.domain.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	//리뷰 작성
	@PostMapping
	public ResponseEntity<ReviewResponseDto> createReview(
		@RequestBody @Valid ReviewRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			reviewService.createReview(requestDto, memberId)
		);
	}

	//리뷰 전체 조회
	@GetMapping
	public ResponseEntity<Page<ReviewStoreResponseDto>> getReviewsByStoreId(
		@RequestParam(required = false) Long storeId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "5") int size,
		@RequestParam(required = false, defaultValue = "1") int minRating,
		@RequestParam(required = false, defaultValue = "5") int maxRating
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			reviewService.getReviewByStoreId(storeId, page, size, minRating, maxRating)
		);
	}
}
