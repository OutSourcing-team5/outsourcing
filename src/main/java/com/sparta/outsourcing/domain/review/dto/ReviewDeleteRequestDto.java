package com.sparta.outsourcing.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewDeleteRequestDto {
	private Long memberId;
	private Long orderId;

	public ReviewDeleteRequestDto(final Long memberId, final Long orderId) {
		this.memberId = memberId;
		this.orderId = orderId;
	}
}
