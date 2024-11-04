package com.sparta.outsourcing.domain.review.dto;

import com.sparta.outsourcing.domain.review.entity.Review;

import lombok.Getter;

@Getter
public class ReviewResponseDto {
	private Long memberId;
	private Long orderId;
	private int rating;

	public ReviewResponseDto(Review review) {
		this.memberId = review.getOrder().getMember().getId();
		this.orderId = review.getOrder().getId();
		this.rating = review.getRating();
	}
}
