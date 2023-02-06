package com.board.exception;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionManager {

    @ExceptionHandler
    public ResponseEntity<?> appExceptionHandler(AppException e) {
        log.info("appExceptionHandler : " +  e.getMessage());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(e.getErrorCode().name() + " " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class) //해당하는 exception 발생하면 처리
    public ResponseEntity<?> runtimeExceptionHandler(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT) //서버의 현재 상태와 요청이 충돌
                .body(e.getMessage());
    }

}
