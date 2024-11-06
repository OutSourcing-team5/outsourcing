package com.sparta.outsourcing.domain.member.dto;

import org.hibernate.annotations.processing.Pattern;

import jakarta.validation.constraints.*;
import lombok.Getter;

@Getter
public class AddPointsRequestDto {
	@NotNull
	private String points;
}
