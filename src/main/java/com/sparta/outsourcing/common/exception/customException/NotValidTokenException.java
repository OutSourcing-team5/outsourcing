package com.sparta.outsourcing.common.exception.customException;

import com.sparta.outsourcing.common.exception.enums.ExceptionCode;

import lombok.Getter;

@Getter
public class NotValidTokenException extends RuntimeException {
    private final ExceptionCode exceptionCode;

    public NotValidTokenException(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
}
