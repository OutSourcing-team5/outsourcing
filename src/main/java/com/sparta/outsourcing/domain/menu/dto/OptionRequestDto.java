package com.sparta.outsourcing.domain.menu.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class OptionRequestDto {
	@NotNull
	private String optionName;

	@NotNull
	private int optionPrice;

	@NotNull
	private Long menuId;
}
