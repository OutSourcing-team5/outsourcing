package com.sparta.outsourcing.domain.review.dto;

import com.sparta.outsourcing.domain.review.entity.Review;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseFromStoreDto {
	private Long memberId;
	private int rating;

	public ReviewResponseFromStoreDto(Review review) {
		this.memberId = review.getId();
		this.rating = review.getRating();
	}
}
