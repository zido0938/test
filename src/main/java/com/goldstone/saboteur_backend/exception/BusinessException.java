package com.goldstone.saboteur_backend.exception;

import com.goldstone.saboteur_backend.exception.code.error.CommonErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

	private final CommonErrorCode errorCode;
}
