package com.ftence.ftwekey.exception;

import com.ftence.ftwekey.exception.login.ValidFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Slf4j
@ComponentScan
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ValidFailException.class})
    public ResponseEntity<Object> handleJwtInvalidException(ValidFailException e) {

        log.warn("[{}] {}", e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {UsernameException.class})
    public ResponseEntity<Object> invalidUserException(UsernameException e) {

        log.warn("[{}] {}", e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(RequestRejectedException.class)
    public ResponseEntity<Object> handleRequestRejectedException(RequestRejectedException e) {

        log.warn("[{}] {}", e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> invalidDataException(NullPointerException e) {

        log.warn("[{}] {}", e.getClass().getSimpleName(), e.getMessage());

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getClass().getSimpleName(), httpStatus, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        return new ResponseEntity<>(apiException, httpStatus);
    }
}
