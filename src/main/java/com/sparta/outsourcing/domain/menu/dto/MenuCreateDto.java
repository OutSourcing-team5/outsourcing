package com.sparta.outsourcing.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MenuCreateDto {
	@NotBlank
	private String menuName;

	private int price;

	private Long storeId;
}
