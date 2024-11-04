package com.sparta.outsourcing.domain.menu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MenuDeleteDto {
	@NotNull(message = "storeId는 필수 입력 항목입니다.")
	private Long storeId;
}
