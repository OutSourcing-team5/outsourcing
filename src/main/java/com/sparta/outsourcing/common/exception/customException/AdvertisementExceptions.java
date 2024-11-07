package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class AdvertisementExceptions extends RuntimeException {
	private final ExceptionCode exceptionCode;

	public AdvertisementExceptions(ExceptionCode exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
}
