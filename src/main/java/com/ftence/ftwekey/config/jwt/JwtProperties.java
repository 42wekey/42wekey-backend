package com.ftence.ftwekey.config.jwt;

public interface JwtProperties {
    int EXPIRATION_TIME = 864000000;    // 10일 (1/1000초)
    String TOKEN_PREFIX = "Bearer ";
    String CONTENT_TYPE = "application/json; charset=utf-8";
}
