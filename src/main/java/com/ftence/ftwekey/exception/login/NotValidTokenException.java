package com.ftence.ftwekey.exception.login;

public class NotValidTokenException extends RuntimeException{

    public NotValidTokenException() {
    }

    public NotValidTokenException(String message) {
        super(message);
    }
}
