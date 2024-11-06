package com.sparta.outsourcing.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewUpdateRequestDto {
	@NotNull(message = "숫자를 입력해주세요.")
	private int rating;

	public ReviewUpdateRequestDto(int rating) {
		this.rating = rating;
	}
}
