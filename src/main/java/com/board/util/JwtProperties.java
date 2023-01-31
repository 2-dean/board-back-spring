package com.board.util;

public interface JwtProperties {

    String SECRET_KEY = "secretkey";
    Long EXPIRATION_TIME = 60000 * 60L;
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
}
