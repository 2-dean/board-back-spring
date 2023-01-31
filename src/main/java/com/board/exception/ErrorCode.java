package com.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATE(HttpStatus.CONFLICT, ""),    //서버의 현재 상태와 요청이 충돌
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND , ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED , "")  //해당 리소스에 유효한 인증 자격 증명이 없음
    ;

    private HttpStatus httpStatus;
    private String message;
}
