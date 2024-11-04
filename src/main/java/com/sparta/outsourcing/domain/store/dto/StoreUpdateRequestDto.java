package com.sparta.outsourcing.domain.store.dto;

import java.sql.Time;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class StoreUpdateRequestDto {
	@NotNull(message = "오픈 시간을 정해주셔야 합니다.")
	private Time openTime;

	@NotNull(message = "마감 시간을 정해주셔야 합니다.")
	private Time closeTime;

	@Positive(message = "최소 금액은 음수가 아닙니다.")
	private int minPrice;

	private boolean isOpened;
}
