package com.sparta.outsourcing.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewStoreResponseDto {
	private Long orderId;
	private int rating;

	public ReviewStoreResponseDto(Long orderId, int rating) {
		this.orderId = orderId;
		this.rating = rating;
	}
}
