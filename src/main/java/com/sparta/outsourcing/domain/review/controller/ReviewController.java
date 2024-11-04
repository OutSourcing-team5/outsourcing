package com.sparta.outsourcing.domain.review.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.outsourcing.domain.review.dto.ReviewDeleteRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing.domain.review.dto.ReviewUpdateRequestDto;
import com.sparta.outsourcing.domain.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewService reviewService;

	@PostMapping
	public ResponseEntity<ReviewResponseDto> createReview(
		@RequestBody @Valid ReviewRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			reviewService.createReview(requestDto, memberId)
		);
	}

	//리뷰 수정
	@PutMapping("/{reviewId}")
	public ResponseEntity<ReviewResponseDto> updateReview(
		@PathVariable Long reviewId,
		@RequestBody ReviewUpdateRequestDto requestDto,
		@RequestAttribute("id") Long memberId
	) {
		return ResponseEntity.status(HttpStatus.OK).body(reviewService.updateReview(reviewId, requestDto, memberId));
	}

	//리뷰 삭제
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Void> deleteReview(
		@PathVariable Long reviewId,
		@RequestAttribute("id") Long memberId
	) {
		reviewService.deleteReview(reviewId, memberId);
		return ResponseEntity.noContent().build();
	}
}
