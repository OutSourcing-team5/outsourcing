package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class MemberExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public MemberExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
