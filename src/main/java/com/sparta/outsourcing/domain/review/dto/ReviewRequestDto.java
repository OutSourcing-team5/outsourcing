package com.sparta.outsourcing.domain.review.dto;

import lombok.Getter;

@Getter
public class ReviewRequestDto {
	private Long memberId;
	private Long orderId;
	private int rating;
}
