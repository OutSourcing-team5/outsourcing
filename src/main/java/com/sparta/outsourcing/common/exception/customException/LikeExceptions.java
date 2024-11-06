package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class LikeExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public LikeExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
