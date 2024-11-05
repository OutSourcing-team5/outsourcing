package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class MenuExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public MenuExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}