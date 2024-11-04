package com.sparta.outsourcing.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
	@NotNull(message = "회원 ID는 필수 입력사항입니다.")
	private Long memberId;
	@NotNull(message = "주문 ID는 필수 입력사항입니다.")
	private Long orderId;
	@NotNull(message = "숫자를 입력해주세요.")
	private int rating;
}
