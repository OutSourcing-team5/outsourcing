package com.sparta.outsourcing.domain.store.dto;

import java.sql.Time;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
public class StoreRequestDto {
	@NotNull(message = "가게 이름은 null 값일 수 없습니다.")
	private String storeName;

	@NotNull(message = "오픈 시간을 정해주셔야 합니다.")
	private Time openTime;

	@NotNull(message = "마감 시간을 정해주셔야 합니다.")
	private Time closeTime;

	@Positive(message = "최소 금액은 음수가 아닙니다.")
	private int minPrice;

	public StoreRequestDto(String storeName, Time openTime, Time closeTime, int minPrice) {
		this.storeName = storeName;
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.minPrice = minPrice;
	}
}
