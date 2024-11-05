package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class OrderExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public OrderExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
