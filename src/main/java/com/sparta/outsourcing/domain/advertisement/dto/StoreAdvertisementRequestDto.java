package com.sparta.outsourcing.domain.advertisement.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreAdvertisementRequestDto {
	@NotNull(message = "가게 id는 빈 값일 수 없습니다.")
	private Long storeId;
}