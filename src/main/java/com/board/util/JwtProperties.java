package com.board.util;

public interface JwtProperties {

    String SECRET_KEY = "secretkey";
    Long EXPIRATION_TIME = 1000 * 60L;
    Long REFRESH_EXPIRATION_TIME = 1000 * 60 * 10L ;

    // String TOKEN_PREFIX = "Bearer";
    // String HEADER_STRING = "Authorization";
}
