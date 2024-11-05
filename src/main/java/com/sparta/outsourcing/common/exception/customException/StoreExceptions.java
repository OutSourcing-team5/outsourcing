package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class StoreExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public StoreExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
