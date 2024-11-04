package com.sparta.outsourcing.domain.menu.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class MenuRequestDto {
	@NotBlank(message = "메뉴 이름은 필수 입력 항목입니다.")
	private String menuName;

	@Min(value = 1, message = "가격은 0보다 커야 합니다.")
	private int price;

	@NotNull(message = "storeId는 필수 입력 항목입니다.")
	private Long storeId;
}
