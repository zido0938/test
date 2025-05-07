package com.goldstone.saboteur_backend.exception.code.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorCode {

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다.")
	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
