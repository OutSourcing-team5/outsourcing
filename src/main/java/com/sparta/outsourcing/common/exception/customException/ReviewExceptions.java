package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class ReviewExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public ReviewExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
