package com.ftence.ftwekey.exception;

import com.ftence.ftwekey.exception.login.ValidFailException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ComponentScan
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ValidFailException.class})
    public ResponseEntity<Object> handleJwtInvalidException(ValidFailException e) {

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        // todo 메세지를 예외마다 다 쪼개가지고 처리해야 함

        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleEmptyValueException(NullPointerException e) {

        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(RequestRejectedException.class)
    public ResponseEntity<Object> handleRequestRejectedException(RequestRejectedException e) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }
}
