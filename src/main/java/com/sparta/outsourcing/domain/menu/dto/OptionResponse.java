package com.sparta.outsourcing.domain.menu.dto;

import com.sparta.outsourcing.domain.menu.entity.Option;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OptionResponse {
	@NotNull
	private String optionName;

	@NotNull
	private int optionPrice;

	public OptionResponse(Option option) {
		this.optionName = option.getOptionName();
		this.optionPrice = option.getOptionPrice();
	}
}
